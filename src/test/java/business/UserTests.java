package business;

import com.excilys.ebi.spring.dbunit.config.DBOperation;
import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.liztube.business.AuthBusiness;
import com.liztube.business.UserBusiness;
import com.liztube.config.JpaConfigs;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.exceptionType.PublicException;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.EnumRole;
import com.liztube.utils.facade.UserFacade;
import com.liztube.utils.facade.UserForRegistration;
import com.liztube.utils.facade.UserPasswordFacade;
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

    public UserFacade userForRegistration;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public UserFacade userUpdate;

    public UserPasswordFacade userPasswordFacade;

    @Before
    public void SetUp(){
        Timestamp birthday = Timestamp.valueOf(LocalDateTime.of(1991, Month.FEBRUARY, 1, 0, 0));
        userUpdate = new UserFacade().setPseudo("userUpdate")
                .setBirthdate(birthday)
                .setEmail("userInfos@hotmail.fr")
                .setFirstname("user")
                .setLastname("user")
                .setIsfemale(false);

        userPasswordFacade = new UserPasswordFacade()
                .setNewPassword("liztube")
                .setOldPassword("cisco");

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
    public void user_should_get_user_infos_successfully() throws UserNotFoundException, UserException{
        userForRegistration = userBusiness.getUserInfo();

        assertThat(userForRegistration).isNotNull();
        assertThat(userForRegistration.getBirthdate()).isEqualTo(userLiztube.getBirthdate());
        assertThat(userForRegistration.getEmail()).isEqualTo(userLiztube.getEmail());

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        //assertThat(userLiztube.getPassword()).isEqualTo(encoder.encodePassword(userForRegistration.getPassword(), null));
        assertThat(userForRegistration.getLastname()).isEqualTo(userLiztube.getLastname());
        assertThat(userForRegistration.getFirstname()).isEqualTo(userLiztube.getFirstname());
    }

    //test pour la modification d'utilisateur

    @Test
    public void firstname_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getFirstname()).isEqualTo("Laurent");
        userUpdate.setFirstname("Youcef");
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getFirstname()).isEqualTo("Youcef");

    }

    @Test
    public void lastname_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getLastname()).isEqualTo("Babin");
        userUpdate.setLastname("BenZ");
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getLastname()).isEqualTo("BenZ");

    }


    @Test
    public void birthdate_shoulb_be_changed_succesfully() throws UserNotFoundException, UserException{
        assertThat(userLiztube.getBirthdate()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2013, Month.OCTOBER, 5, 10, 15, 26)));
        userUpdate.setBirthdate(Timestamp.valueOf(LocalDateTime.of(2005, Month.MAY, 6, 0, 0)));
        userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getBirthdate()).isEqualTo(Timestamp.valueOf(LocalDateTime.of(2005, Month.MAY, 6, 0, 0)));

    }

    @Test
    public void check_old_password_should_be_correspond()throws UserNotFoundException, UserException {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        assertThat(userLiztube.getPassword()).isEqualTo(encoder.encodePassword(userPasswordFacade.getOldPassword(), null));
    }


    @Test
    public void password_should_be_encrypted() throws UserNotFoundException, UserException {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        assertThat(userLiztube.getPassword()).isEqualTo(encoder.encodePassword(userPasswordFacade.getOldPassword(), null));

        userBusiness.changeUserPassword(userPasswordFacade);
        UserLiztube userLiztube = userBusiness.updateUserInfo(userUpdate);
        assertThat(userLiztube.getPassword()).isEqualTo(encoder.encodePassword(userPasswordFacade.getNewPassword(),null));
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

    @Test
    public void should_raise_an_error_if_password_too_short() throws UserNotFoundException, UserException {
        userPasswordFacade = userPasswordFacade.setNewPassword("cisc");//4 characters
        try{
            userBusiness.changeUserPassword(userPasswordFacade);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.SIGNIN_PASSWORD_FORMAT);
        }
    }

    @Test
    public void should_raise_an_error_if_password_too_long() throws UserNotFoundException, UserException {
        userPasswordFacade = userPasswordFacade.setNewPassword("ciscociscociscociscociscociscociscociscociscociscoc");//51 characters
        try{
            userBusiness.changeUserPassword(userPasswordFacade);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.SIGNIN_PASSWORD_FORMAT);
        }
    }

    @Test
    public void should_raise_an_error_if_firstname_empty() throws UserNotFoundException, UserException {
        userUpdate = userUpdate.setFirstname("");
        try{
            userBusiness.updateUserInfo(userUpdate);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.SIGNIN_FIRSTNAME_SIZE);
        }
    }

    @Test
    public void should_raise_an_error_if_birthday_is_not_a_past_date() throws UserNotFoundException, UserException {
        userUpdate = userUpdate.setBirthdate(Timestamp.valueOf(LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0)));
        try{
            userBusiness.updateUserInfo(userUpdate);
            fail("Should throw exception");
        }catch (PublicException e){
            assertThat(e.getMessages()).contains(EnumError.SIGNIN_BIRTHDAY_PAST_DATE);
        }
    }
}
