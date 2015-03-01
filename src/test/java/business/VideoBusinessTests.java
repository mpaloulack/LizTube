package business;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.VideoBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/UserDataset.xml", tearDownOperation = DBOperation.DELETE_ALL)
public class VideoBusinessTests {

    @Autowired
    VideoBusiness videoBusiness;

    private ClassPathResource files = new ClassPathResource("files/");

    @Test
    public void uploadVideo_should_return_filename() throws IOException, UserNotFoundException {
        FileInputStream inputFile = new FileInputStream(files.getFile().getAbsolutePath() + File.separator +"video.mp4");
        MockMultipartFile file = new MockMultipartFile("file", "video.mp4", "multipart/form-data", inputFile);

        String result = videoBusiness.uploadVideo(file, "title", "description", false, false);
        assertThat(result).isEqualTo("video.mp4");
    }
}
