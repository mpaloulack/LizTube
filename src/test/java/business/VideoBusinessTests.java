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
import com.liztube.utils.facade.VideoCreationFacade;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/UserDataset.xml")
@TestPropertySource("/application.testing.properties")
public class VideoBusinessTests {

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

    @Test
    public void uploadVideo_should_raise_exception_if_not_mp4() throws IOException, UserNotFoundException, VideoException {
        assertThat(videoRepository.findAll().size()).isEqualTo(0);
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
        assertThat(videoRepository.findAll().size()).isEqualTo(0);
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String result = videoBusiness.uploadVideo(file, videoCreationFacade);
        assertThat(result.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")).isTrue();
    }

    @Test
    public void uploadVideo_should_raise_error_if_title_size_incorrect() throws IOException, UserNotFoundException {
        assertThat(videoRepository.findAll().size()).isEqualTo(0);
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
        assertThat(videoRepository.findAll().size()).isEqualTo(0);
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
        assertThat(videoRepository.findAll().size()).isEqualTo(0);
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
        assertThat(videoRepository.findAll().size()).isEqualTo(0);
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String key = videoBusiness.uploadVideo(file, videoCreationFacade);
        Video videoPersist = videoRepository.findByKey(key);
        assertThat(videoPersist).isNotNull();
        assertThat(videoPersist.getTitle()).isEqualTo(videoCreationFacade.getTitle());
        assertThat(videoPersist.getDescription()).isEqualTo(videoCreationFacade.getDescription());
        assertThat(videoPersist.getIspublic()).isEqualTo(videoCreationFacade.isPublic());
        assertThat(videoPersist.getIspubliclink()).isEqualTo(videoCreationFacade.isPublicLink());
        assertThat(videoRepository.findAll().size()).isEqualTo(1);
    }
}
