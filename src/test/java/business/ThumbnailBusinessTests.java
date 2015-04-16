package business;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.ThumbnailBusiness;
import com.liztube.business.VideoBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.exception.ThumbnailException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.exception.exceptionType.PublicException;
import com.liztube.utils.EnumRole;
import com.liztube.utils.facade.video.VideoCreationFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/VideoDataset.xml")
@TestPropertySource("/application.testing.properties")
public class ThumbnailBusinessTests {

    @Autowired
    VideoBusiness videoBusiness;
    @Autowired
    ThumbnailBusiness thumbnailBusiness;

    private ClassPathResource files = new ClassPathResource("files/");
    private VideoCreationFacade videoCreationFacade;
    private final static int THUMBNAIL_UNAVAILABLE_SIZE = 31250;
    private final static int THUMBNAIL_DEFAULT_SIZE = 27031;
    private final static int THUMBNAIL_VIDEO_SIZE = 79345;

    //region preparation
    @Before
    public void setUp() throws IOException {
        videoCreationFacade = new VideoCreationFacade().setTitle("title").setDescription("description").setPublic(false).setPublicLink(false);

        //User connected
        List<GrantedAuthority> userAuthorities=new ArrayList<GrantedAuthority>(2);
        userAuthorities.add(new SimpleGrantedAuthority(EnumRole.AUTHENTICATED.toString()));
        userAuthorities.add(new SimpleGrantedAuthority(EnumRole.USER.toString()));
        User adminSpringUser = new User("spywen","cisco", userAuthorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(adminSpringUser,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @After
    public void setDown() throws IOException {
        //Remove all files inside the video library AND video thumbnails library after each tests
        File videoLibraryFolder = new File(videoBusiness.videoLibrary.getFile().getAbsolutePath() + File.separator);
        File videoThumbnailLibraryFolder = new File(thumbnailBusiness.videoThumbnailsLibrary.getFile().getAbsolutePath() + File.separator);
        if(videoLibraryFolder.exists()){
            for(File file : videoLibraryFolder.listFiles()){
                file.delete();
            }
            for(File file : videoThumbnailLibraryFolder.listFiles()){
                file.delete();
            }
        }
    }
    //endregion

    //region create
    @Test
    public void should_create_thumbnail_with_adpated_size() throws UserNotFoundException, VideoException, ThumbnailException, IOException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String key = videoBusiness.uploadVideo(file, videoCreationFacade);

        thumbnailBusiness.createDefaultThumbnail(key);
        File thumbnail = new File(String.format(thumbnailBusiness.filePathForFormat, thumbnailBusiness.videoThumbnailsLibrary.getFile().getAbsolutePath(), File.separator, key + thumbnailBusiness.VIDEO_DEFAULT_THUMBNAIL_DEFAULT_IMAGE_SUFFIX));

        assertThat(thumbnail).exists();
        assertThat(thumbnail).isFile();

        BufferedImage thumbBuff = ImageIO.read(thumbnail);
        assertThat(thumbBuff.getWidth()).isEqualTo(320);
        assertThat(thumbBuff.getHeight()).isEqualTo(180);
    }

    @Test
    public void should_raise_an_error_if_problem_occured_while_creating_thumbnail() throws UserNotFoundException, VideoException, ThumbnailException, IOException {
        try{
            thumbnailBusiness.createDefaultThumbnail("NOTEXIST");
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(ThumbnailBusiness.VIDEO_CREATE_DEFAULT_THUMBNAIL);
        }
    }
    //endregion

    //region get
    @Test
    public void should_get_video_thumbnail() throws UserNotFoundException, VideoException, ThumbnailException, IOException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String key = videoBusiness.uploadVideo(file, videoCreationFacade);

        byte[] image = thumbnailBusiness.getThumbnail(key);
        assertThat(image.length).isEqualTo(THUMBNAIL_VIDEO_SIZE);
    }

    @Test
    public void should_get_unavailable_thumbnail_if_not_video_exist() throws UserNotFoundException, VideoException, ThumbnailException, IOException {
        byte[] image = thumbnailBusiness.getThumbnail("NOTEXIST");
        assertThat(image.length).isEqualTo(THUMBNAIL_UNAVAILABLE_SIZE);
    }

    @Test
    public void should_get_unavailable_thumbnail_if_user_dont_have_right() throws UserNotFoundException, VideoException, ThumbnailException, IOException {
        byte[] image = thumbnailBusiness.getThumbnail("f");
        assertThat(image.length).isEqualTo(THUMBNAIL_UNAVAILABLE_SIZE);
    }

    @Test
    public void should_get_default_thumbnail_if_thumbnail_not_exist() throws UserNotFoundException, VideoException, ThumbnailException, IOException {
        byte[] image = thumbnailBusiness.getThumbnail("a");
        assertThat(image.length).isEqualTo(THUMBNAIL_DEFAULT_SIZE);
    }
    //endregion
}