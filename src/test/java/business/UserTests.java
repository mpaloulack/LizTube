package business;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.AuthBusiness;
import com.liztube.business.UserBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserException;
import com.liztube.exception.UserException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.exceptionType.PublicException;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.EnumRole;
import com.liztube.utils.facade.UserForRegistration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfigs.class}, loader = AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@DataSet(value = "/data/UserDataset.xml", tearDownOperation = DBOperation.DELETE_ALL)
public class UserTests {

    @Autowired
    AuthBusiness authBusiness;

    @Autowired
    UserBusiness userBusiness;

    @Autowired
    UserLiztubeRepository userLiztubeRepository;

    public UserLiztube userLiztube;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public UserForRegistration userUpdate;

    @Before
    public void SetUp(){
        Timestamp birthday = Timestamp.valueOf(LocalDateTime.of(1991, Month.FEBRUARY, 1, 0, 0));
        userUpdate = new UserForRegistration().setPseudo("userUpdate")
                .setBirthdate(birthday)
                .setEmail("userInfos@hotmail.fr")
                .setFirstname("user")
                .setLastname("user")
                .setIsfemale(false)
                .setPassword("cisco");

        userLiztube = userLiztubeRepository.findByPseudo("spywen");

        //User connected
        List<GrantedAuthority> userAuthorities=new ArrayList<GrantedAuthority>(2);
        userAuthorities.add(new SimpleGrantedAuthority(EnumRole.AUTHENTICATED.toString()));
        userAuthorities.add(new SimpleGrantedAuthority(EnumRole.USER.toString()));
        User adminSpringUser = new User("spywen","cisco", userAuthorities);
        Authentication auth = new UsernamePasswordAuthenticationToken(adminSpringUser,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    //test de la récupération des infos utilisateurs
    @Test
    public void userUpdate_should_persist_user_if_all_tests_passed_successfully() throws UserNotFoundException, UserException{
        userLiztube = userBusiness.updateUserInfo(userUpdate);

        assertThat(userLiztube).isNotNull();
        assertThat(userLiztube.getBirthdate()).isEqualTo(userUpdate.getBirthdate());
        assertThat(userLiztube.getEmail()).isEqualTo(userUpdate.getEmail());

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        assertThat(userLiztube.getPassword()).isEqualTo(encoder.encodePassword(userUpdate.getPassword(), null));
        assertThat(userLiztube.getLastname()).isEqualTo(userUpdate.getLastname());
        assertThat(userLiztube.getFirstname()).isEqualTo(userUpdate.getFirstname());
        assertThat(userLiztube.getIsfemale()).isEqualTo(userUpdate.getIsfemale());
    }

    //test pour la modification d'utilisateur

    @Test
    public void firstname_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getFirstname()).isEqualToIgnoringCase("laurent");
        userUpdate.setFirstname("Youcef");
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getFirstname()).isEqualToIgnoringCase("Youcef");

    }

    @Test
    public void lastname_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getLastname()).isEqualToIgnoringCase("Babin");
        userUpdate.setLastname("BenZ");
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getLastname()).isEqualToIgnoringCase("BenZ");

    }

    @Test
    public void isfemale_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getIsfemale()).isFalse();
        userUpdate.setIsfemale(true);
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getIsfemale()).isTrue();

    }

    @Test
    public void birthdate_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getBirthdate()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2013, Month.OCTOBER, 5, 10, 15, 26)));
        userUpdate.setBirthdate(Timestamp.valueOf(LocalDateTime.of(2005, Month.MAY, 6, 0, 0)));
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getBirthdate()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2005, Month.MAY, 6, 0, 0)));

    }

    @Test
    public void password_should_be_not_changed() throws  UserException, UserNotFoundException{
        userUpdate.setPassword("");
        assertThat(userUpdate.getPassword()).isEqualTo("");
        UserLiztube userLiztube = userBusiness.updateUserInfo(userUpdate);

        assertThat(userLiztube.getPassword()).isNotEmpty();
    }


    @Test
    public void date_modification_changed_should_be_filled() throws UserException, UserNotFoundException {
        Timestamp before = Timestamp.valueOf(LocalDateTime.now());
        UserLiztube userLiztube = userBusiness.updateUserInfo(userUpdate);
        Timestamp after = Timestamp.valueOf(LocalDateTime.now());

        assertThat(userLiztube.getModificationdate()).isBetween(before, after);
    }

    //Other errors manage by entity validations

   @Test
    public void should_raise_an_error_if_email_already_used(){
        userUpdate = userUpdate.setEmail("kmille@hotmail.fr");

        try{
            userBusiness.updateUserInfo(userUpdate);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.SIGNIN_EMAIL_OR_PSEUDO_ALREADY_USED);
        }
    }
}
