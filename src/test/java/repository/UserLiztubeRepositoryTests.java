package repository;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.config.JpaConfigs;
import com.liztube.entity.UserLiztube;
import com.liztube.repository.UserLiztubeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/UserDataset.xml", tearDownOperation = DBOperation.DELETE_ALL)
public class UserLiztubeRepositoryTests {
    @Autowired
    UserLiztubeRepository userLiztubeRepository;

    @Test
    public void should_find_by_email_or_pseudo(){
        String email = "spywen@hotmail.fr";
        String pseudo = "spywen";
        UserLiztube user1 = userLiztubeRepository.findByEmailOrPseudo(email, email);
        UserLiztube user2 = userLiztubeRepository.findByEmailOrPseudo(pseudo,pseudo);
        assertThat(user1).isNotNull();
        assertThat(user2).isNotNull();
        assertThat(user1).isEqualToComparingFieldByField(user2);
    }

    @Test
    public void should_find_by_pseudo(){
        assertThat(userLiztubeRepository.findByPseudo("kmille")).isNotNull();
    }

    @Test
    public void should_count_one_user_with_pseudo_kmille(){
        assertThat(userLiztubeRepository.countByPseudo("kmille")).isEqualTo(1);
    }

    @Test
    public void should_count_zero_user_with_pseudo_unknown(){
        assertThat(userLiztubeRepository.countByPseudo("unknown")).isEqualTo(0);
    }

    @Test
    public void should_count_one_user_with_email_spywen(){
        assertThat(userLiztubeRepository.countByEmail("spywen@hotmail.fr")).isEqualTo(1);
    }

    @Test
    public void should_count_zero_user_with_email_unknown(){
        assertThat(userLiztubeRepository.countByEmail("unknown")).isEqualTo(0);
    }

}
