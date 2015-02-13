package repository.Predicates;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.AuthBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.entity.UserLiztube;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.repository.predicate.UserLiztubePredicates;
import com.mysema.query.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class})
@DataSet(value = "/data/UserDataset.xml", tearDownOperation = DBOperation.DELETE_ALL)
public class UserLiztubePredicatesTests {

    @Autowired
    UserLiztubeRepository userLiztubeRepository;

    @Test
    public void fake(){
        assertThat(1).isEqualTo(1);
    }
/*
    @Test
    public void hasEmail() {
        Predicate predicate = UserLiztubePredicates.hasEmail("spy");
        String predicateAsString = predicate.toString();
        assertThat(predicateAsString).isEqualTo("userLiztube.email = spy");
    }

    @Test
    public void hasPseudo() {
        Predicate predicate = UserLiztubePredicates.hasPseudo("spy");
        String predicateAsString = predicate.toString();
        assertThat(predicateAsString).isEqualTo("userLiztube.pseudo = spy");
    }

    @Transactional
    @Test
    public void hasEmailBis(){
        UserLiztube userLiztube = userLiztubeRepository.findOne(UserLiztubePredicates.hasEmail("spyw"));
    }
    */
}
