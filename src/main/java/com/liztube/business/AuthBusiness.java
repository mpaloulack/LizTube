package com.liztube.business;

import com.liztube.entity.Role;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.SigninException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.repository.RoleRepository;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.EnumRole;
import com.liztube.utils.facade.SigninTestExistFacade;
import com.liztube.utils.facade.UserConnectedProfile;
import com.liztube.utils.facade.UserForRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.*;
import java.time.LocalDateTime;

/**
 * Class which manage all the method concerning the authentication of an user
 */
@Component
public class AuthBusiness {
    @Autowired
    public UserLiztubeRepository userLiztubeRepository;

    public static final String USER_NOT_FOUND_EXCEPTION = "You are not connected.";
    public static final String PASSWORD_REGEX = "^.{5,50}$";//Password should contain between 5 and 50 characters (a password with 5 or 50 characters is valid)

    @Autowired
    public RoleRepository roleRepository;

    /**
     * Get partial user profile : Username (login) and roles
     * @return
     */
    public UserConnectedProfile getUserConnectedProfile(){
        List<String> roles = new ArrayList<String>();
        try{
            UserLiztube user = getConnectedUser();
            for(Role role : user.getRoles()){
                roles.add(role.getName());
            }
            return new UserConnectedProfile(user.getPseudo(),roles);
        }catch (Exception e){
            return new UserConnectedProfile("",new ArrayList<String>());
        }
    }

    /**
     * Define if the an email is already registered
     * @param signinTestExistFacade
     * @return
     */
    public Boolean existEmail(SigninTestExistFacade signinTestExistFacade){
        if(userLiztubeRepository.countByEmail(signinTestExistFacade.getValue()) == 0){
            return false;
        }
        return true;
    }

    /**
     * Define if the a pseudo is already registered
     * @param signinTestExistFacade
     * @return
     */
    public Boolean existPseudo(SigninTestExistFacade signinTestExistFacade){
        if(userLiztubeRepository.countByPseudo(signinTestExistFacade.getValue()) == 0){
            return false;
        }
        return true;
    }

    /**
     * Get complete connected user profile
     * @return
     * @throws com.liztube.exception.UserNotFoundException
     */
    public UserLiztube getConnectedUser() throws UserNotFoundException {
        if (SecurityContextHolder.getContext() != null &&
                SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            try{
                User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String login = user.getUsername();
                return userLiztubeRepository.findByEmailOrPseudo(login);
            }catch (Exception e){
                throw new UserNotFoundException("Find user by login failed", USER_NOT_FOUND_EXCEPTION);
            }
        }
        throw new UserNotFoundException("Session not found", USER_NOT_FOUND_EXCEPTION);
    }

    /**
     * Registered a new user
     * @param userForRegistration
     * @return new user registered
     */
    public UserLiztube signIn(UserForRegistration userForRegistration) throws SigninException {
        Role userRole = roleRepository.findByName(EnumRole.USER.toString());
        Role authenticatedRole = roleRepository.findByName(EnumRole.AUTHENTICATED.toString());
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

        //Persist user
        UserLiztube user = new UserLiztube()
                .setBirthdate(userForRegistration.getBirthdate())
                .setPseudo(userForRegistration.getPseudo())
                .setEmail(userForRegistration.getEmail())
                .setFirstname(userForRegistration.getFirstname())
                .setLastname(userForRegistration.getLastname())
                .setIsfemale(userForRegistration.getIsfemale())
                .setPassword(encoder.encodePassword(userForRegistration.getPassword(), null))
                .setIsactive(true)
                .setModificationdate(Timestamp.valueOf(LocalDateTime.now()))
                .setRegisterdate(Timestamp.valueOf(LocalDateTime.now()));

        //User already exist
        if(existEmail(new SigninTestExistFacade().setValue(user.getEmail())) || existPseudo(new SigninTestExistFacade().setValue(user.getPseudo()))){
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(EnumError.SIGNIN_EMAIL_OR_PSEUDO_ALREADY_USED);
            throw new SigninException("email or pseudo already exist", errorMessages);
        }

        //Password well formatted
        if(!userForRegistration.getPassword().matches(PASSWORD_REGEX)){
            throw new SigninException("Not valid format for password", EnumError.SIGNIN_PASSWORD_FORMAT);
        }

        //Entity validations Validations
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserLiztube>> constraintViolations = validator.validate(user);
        if(constraintViolations.size() > 0){
            List<String> errorMessages = new ArrayList<>();
            for(ConstraintViolation<UserLiztube> constraintViolation : constraintViolations){
                errorMessages.add(constraintViolation.getMessage());
            }
            throw new SigninException("signin", errorMessages);
        }


        user = userLiztubeRepository.saveAndFlush(user);

        //Add USER and AUTHENTICATED role to the user
        user.setRoles(new HashSet<>(Arrays.asList(userRole, authenticatedRole)));
        userLiztubeRepository.save(user);

        return user;
    }


}
