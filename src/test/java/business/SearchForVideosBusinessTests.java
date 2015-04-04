package business;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.SearchForVideosBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.utils.facade.video.GetVideosFacade;
import com.liztube.utils.facade.video.VideoSearchFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/SearchVideosDataset.xml")
@TestPropertySource("/application.testing.properties")
public class SearchForVideosBusinessTests {

    @Autowired
    SearchForVideosBusiness searchForVideosBusiness;

    @Test
    public void defaultSearch_should_return_5_latest_videos(){
        VideoSearchFacade videoSearchFacade = new VideoSearchFacade().setPage(1);
        GetVideosFacade videosFound = searchForVideosBusiness.GetVideos(videoSearchFacade);
        assertThat(videosFound.getVideos().size()).isEqualTo(5);
        assertThat(videosFound.getVideos().get(0).getTitle()).isEqualTo("K video");
        assertThat(videosFound.getVideos().get(4).getTitle()).isEqualTo("G video");
    }

    @Test
    public void defaultSearch_should_return_correct_additonnal_data(){
        VideoSearchFacade videoSearchFacade = new VideoSearchFacade().setPage(1);
        GetVideosFacade videosFound = searchForVideosBusiness.GetVideos(videoSearchFacade);
        assertThat(videosFound.getCurrentPage()).isEqualTo(1);
        assertThat(videosFound.getVideosTotalCount()).isEqualTo(11);
        assertThat(videosFound.getVideosTotalPage()).isEqualTo(3);
    }

    @Test
    public void defaultSearch_should_return_5_latest_videos_on_page_2(){
        VideoSearchFacade videoSearchFacade = new VideoSearchFacade().setPage(2);
        GetVideosFacade videosFound = searchForVideosBusiness.GetVideos(videoSearchFacade);
        assertThat(videosFound.getVideos().size()).isEqualTo(5);
        assertThat(videosFound.getVideos().get(0).getTitle()).isEqualTo("F video");
        assertThat(videosFound.getVideos().get(4).getTitle()).isEqualTo("B video");
    }

    @Test
    public void defaultSearch_should_return_5_latest_videos_on_page_3(){
        VideoSearchFacade videoSearchFacade = new VideoSearchFacade().setPage(3);
        GetVideosFacade videosFound = searchForVideosBusiness.GetVideos(videoSearchFacade);
        assertThat(videosFound.getVideos().size()).isEqualTo(1);
        assertThat(videosFound.getVideos().get(0).getTitle()).isEqualTo("A video");
    }

    @Test
    public void defaultSearch_should_return_videos_data(){
        VideoSearchFacade videoSearchFacade = new VideoSearchFacade().setPage(3);
        GetVideosFacade videosFound = searchForVideosBusiness.GetVideos(videoSearchFacade);
        assertThat(videosFound.getVideos().size()).isEqualTo(1);
        assertThat(videosFound.getVideos().get(0).getTitle()).isEqualTo("A video");
        assertThat(videosFound.getVideos().get(0).getDescription()).isEqualTo("desc of a");
        assertThat(videosFound.getVideos().get(0).getUrl()).isEqualTo("/video?key=a");
        assertThat(videosFound.getVideos().get(0).getViews()).isEqualTo(2);
    }
}
