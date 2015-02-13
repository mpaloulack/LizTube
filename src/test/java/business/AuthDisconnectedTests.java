package business;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.AuthBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.exception.UserNotFoundException;
import com.liztube.utils.facade.SigninTestExistFacade;
import com.liztube.utils.facade.UserConnectedProfile;
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
public class AuthDisconnectedTests {
    @Autowired
    AuthBusiness authBusiness;

    @Test
    public void getUserConnectedProfile_should_get_no_one(){
        UserConnectedProfile userConnectedProfile = authBusiness.getUserConnectedProfile();
        assertThat(userConnectedProfile.getPseudo()).isEqualTo("");
        assertThat(userConnectedProfile.getRoles()).isEmpty();
    }

    @Test(expected = UserNotFoundException.class)
    public void getConnectedUser_should_raise_an_error() throws UserNotFoundException {
        authBusiness.getConnectedUser();
    }

    @Test
    public void existEmail_should_find_someone_with_same_email(){
        assertThat(authBusiness.existEmail(new SigninTestExistFacade().setValue("spywen@hotmail.fr"))).isTrue();
    }

    @Test
    public void existEmail_should_not_find_someone_with_same_email(){
        assertThat(authBusiness.existEmail(new SigninTestExistFacade().setValue("unknown@hotmail.fr"))).isFalse();
    }

    @Test
    public void existPseudo_should_find_someone_with_same_pseudo(){
        assertThat(authBusiness.existPseudo(new SigninTestExistFacade().setValue("spywen"))).isTrue();
    }

    @Test
    public void existPseudo_should_not_find_someone_with_same_pseudo(){
        assertThat(authBusiness.existEmail(new SigninTestExistFacade().setValue("unknown"))).isFalse();
    }
}
