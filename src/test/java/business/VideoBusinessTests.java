package business;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.VideoBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.entity.Video;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;
import com.liztube.exception.exceptionType.PublicException;
import com.liztube.repository.VideoRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.EnumRole;
import com.liztube.utils.facade.video.VideoCreationFacade;
import com.liztube.utils.facade.video.VideoDataFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/VideoDataset.xml")
@TestPropertySource("/application.testing.properties")
public class VideoBusinessTests {

    //region preparation
    @Autowired
    VideoBusiness videoBusiness;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    Environment environment;

    private ClassPathResource files = new ClassPathResource("files/");
    private VideoCreationFacade videoCreationFacade;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp(){
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
        //Remove all files inside the video library after each tests
        File videoLibraryFolder = new File(videoBusiness.videoLibrary.getFile().getAbsolutePath() + File.separator);
        if(videoLibraryFolder.exists()){
            for(File file : videoLibraryFolder.listFiles()){
                file.delete();
            }
        }
    }
    //endregion

    //region upload
    @Test
    public void uploadVideo_should_raise_exception_if_not_mp4() throws IOException, UserNotFoundException, VideoException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.m4a");
        MockMultipartFile file = new MockMultipartFile("file", "video.m4a", "multipart/form-data", inputFile);
        try{
            videoBusiness.uploadVideo(file, videoCreationFacade);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(videoBusiness.VIDEO_UPLOAD_NO_VALID_TYPE);
        }
    }

    @Test
    public void uploadVideo_should_raise_exception_if_exceed_max_file_size() throws IOException, UserNotFoundException, VideoException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"heavyVideo.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);
        try{
            videoBusiness.uploadVideo(file, videoCreationFacade);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(videoBusiness.VIDEO_UPLOAD_TOO_HEAVY);
        }
    }

    @Test
    public void uploadVideo_should_return_key() throws IOException, UserNotFoundException, VideoException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String result = videoBusiness.uploadVideo(file, videoCreationFacade);
        assertThat(result.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")).isTrue();
    }

    @Test
    public void uploadVideo_should_raise_error_if_title_size_incorrect() throws IOException, UserNotFoundException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);
        videoCreationFacade.setTitle("");
        try{
            videoBusiness.uploadVideo(file, videoCreationFacade);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.VIDEO_TITLE_SIZE);
        }
    }

    @Test
    public void uploadVideo_should_raise_error_if_description_size_incorrect() throws IOException, UserNotFoundException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);
        videoCreationFacade.setDescription("");
        try{
            videoBusiness.uploadVideo(file, videoCreationFacade);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.VIDEO_DESCRIPTION_SIZE);
        }
    }

    @Test
    public void uploadVideo_should_save_file_on_server() throws IOException, UserNotFoundException, VideoException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        File videoLibraryFolder = new File(videoBusiness.videoLibrary.getFile().getAbsolutePath() + File.separator);
        assertThat(videoLibraryFolder.list().length).isEqualTo(0);

        String key = videoBusiness.uploadVideo(file, videoCreationFacade);
        File fileFound = new File(videoBusiness.videoLibrary.getFile().getAbsolutePath() + File.separator + key);
        assertThat(videoLibraryFolder.list().length).isEqualTo(1);
        assertThat(fileFound.exists()).isTrue();
    }

    @Test
    public void uploadVideo_should_persist_video_if_all_tests_passed_successfully() throws IOException, UserNotFoundException, VideoException {
        assertThat(videoRepository.findAll().size()).isEqualTo(6);
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String key = videoBusiness.uploadVideo(file, videoCreationFacade);
        Video videoPersist = videoRepository.findByKey(key);
        assertThat(videoPersist).isNotNull();
        assertThat(videoPersist.getTitle()).isEqualTo(videoCreationFacade.getTitle());
        assertThat(videoPersist.getDescription()).isEqualTo(videoCreationFacade.getDescription());
        assertThat(videoPersist.getIspublic()).isEqualTo(videoCreationFacade.isPublic());
        assertThat(videoPersist.getIspubliclink()).isEqualTo(videoCreationFacade.isPublicLink());
        assertThat(videoPersist.getViews().size()).isEqualTo(0);
        assertThat(videoPersist.getCreationdate()).isEqualToIgnoringSeconds(Timestamp.valueOf(LocalDateTime.now()));
        assertThat(videoRepository.findAll().size()).isEqualTo(7);
    }
    //endregion

    //region get
    @Test
    public void getVideo_should_return_video_data() throws VideoException, UserNotFoundException {
        Video videoInDb = videoRepository.findByKey("a");
        VideoDataFacade videoFound = videoBusiness.get("a");
        assertThat(videoFound.getKey()).isEqualTo("a");
        assertThat(videoFound.getTitle()).isEqualTo(videoInDb.getTitle());
        assertThat(videoFound.getDescription()).isEqualTo(videoInDb.getDescription());
        assertThat(videoFound.getCreationDate()).isEqualTo(videoInDb.getCreationdate());
        assertThat(videoFound.getOwnerId()).isEqualTo(videoInDb.getOwner().getId());
        assertThat(videoFound.getOwnerPseudo()).isEqualTo(videoInDb.getOwner().getPseudo());
        assertThat(videoFound.isPublic()).isTrue();
        assertThat(videoFound.isPublicLink()).isTrue();
        assertThat(videoFound.getViews()).isEqualTo(2);
    }

    @Test
    public void getVideo_should_return_an_error_if_video_not_found() {
        try{
            videoBusiness.get("KEYWHICHNOTEXIST");
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(videoBusiness.VIDEO_NOT_FOUND);
        }
    }

    @Test
    public void getVideo_should_return_an_error_if_video_is_private() {
        try{
            videoBusiness.get("f");
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(videoBusiness.VIDEO_NOT_AVAILABLE);
        }
    }

    @Test
    public void getVideo_should_return_video_if_private_but_asked_by_owner() throws VideoException, UserNotFoundException {
        VideoDataFacade videoFound = videoBusiness.get("d");
        assertThat(videoFound.getKey()).isEqualTo("d");
        assertThat(videoFound.isPublic()).isFalse();
        assertThat(videoFound.isPublicLink()).isFalse();
    }

    @Test
    public void getVideo_should_return_raised_an_error_if_video_private_and_user_not_connected() throws VideoException, UserNotFoundException {
        SecurityContextHolder.getContext().setAuthentication(null);
        try{
            videoBusiness.get("f");
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(videoBusiness.VIDEO_NOT_AVAILABLE);
        }
    }

    @Test
    public void getVideo_should_return_video_if_private_but_have_public_link() throws VideoException, UserNotFoundException {
        VideoDataFacade videoFound = videoBusiness.get("e");
        assertThat(videoFound.getKey()).isEqualTo("e");
        assertThat(videoFound.isPublic()).isFalse();
        assertThat(videoFound.isPublicLink()).isTrue();
    }
    //endregion
}
