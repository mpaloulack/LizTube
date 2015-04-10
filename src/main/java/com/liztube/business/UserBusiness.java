package com.liztube.business;

import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.facade.TestExistFacade;
import com.liztube.utils.facade.UserFacade;
import com.liztube.utils.facade.UserPasswordFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class which manage all the method concerning the  user
 */
@Component
public class UserBusiness {

    @Autowired
    public UserLiztubeRepository userLiztubeRepository;

    @Autowired
    public AuthBusiness authBusiness;

    /**
     * Get user information
     * @return userInfo
     * @throws com.liztube.exception.UserNotFoundException
     */
    public UserFacade getUserInfo() throws UserNotFoundException {
        UserLiztube userLiztube = authBusiness.getConnectedUser(true);

        UserFacade userInfo = new UserFacade()
                .setEmail(userLiztube.getEmail())
                .setFirstname(userLiztube.getFirstname())
                .setLastname(userLiztube.getLastname())
                .setBirthdate(userLiztube.getBirthdate())
                .setPseudo(userLiztube.getPseudo())
                .setIsfemale(userLiztube.getIsfemale());

        return userInfo;
    }

    /**
     * PUT user information
     * @return userInfo
     * @throws com.liztube.exception.UserNotFoundException
     */
    public UserLiztube updateUserInfo(UserFacade userInfo) throws UserNotFoundException, UserException {
        UserLiztube userLiztube = authBusiness.getConnectedUser(true);


        //update user persistent
        userLiztube
                .setFirstname(userInfo.getFirstname())
                .setLastname(userInfo.getLastname())
                .setBirthdate(userInfo.getBirthdate())
                .setModificationdate(Timestamp.valueOf(LocalDateTime.now()));


        //verify email if modify
        if (!userInfo.getEmail().equals(userLiztube.getEmail())){
            if(authBusiness.existEmail(new TestExistFacade().setValue(userInfo.getEmail()))){
                List<String> errorMessages = new ArrayList<>();
                errorMessages.add(EnumError.SIGNIN_EMAIL_OR_PSEUDO_ALREADY_USED);
                throw new UserException("email already exist", errorMessages);
            }
            userLiztube.setEmail(userInfo.getEmail());
        }

        //Entity validations Validations
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserLiztube>> constraintViolations = validator.validate(userLiztube);
        if(constraintViolations.size() > 0){
            List<String> errorMessages = new ArrayList<>();
            for(ConstraintViolation<UserLiztube> constraintViolation : constraintViolations){
                errorMessages.add(constraintViolation.getMessage());
            }
            throw new UserException("Not valid format entity", errorMessages);
        }

        userLiztubeRepository.saveAndFlush(userLiztube);

        return userLiztube;
    }

    /**
     * Change password user
     * @param userPassword
     * @return
     * @throws UserNotFoundException
     * @throws UserException
     */
    public String changeUserPassword(UserPasswordFacade userPassword) throws UserNotFoundException, UserException{
        UserLiztube userLiztube = authBusiness.getConnectedUser(true);

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        //check old password
        if (userLiztube.getPassword().equals(encoder.encodePassword(userPassword.getOldPassword(), null))){
            //Password well formatted
            if(!userPassword.getNewPassword().matches(authBusiness.PASSWORD_REGEX)){
                throw new UserException("Not valid format for password", EnumError.SIGNIN_PASSWORD_FORMAT);
            }
            userLiztube.setPassword(encoder.encodePassword(userPassword.getNewPassword(), null));
        }else{
            throw new UserException("old password not corresponding ", EnumError.SIGNIN_PASSWORD_FORMAT);
        }

        //Entity validations Validations
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserLiztube>> constraintViolations = validator.validate(userLiztube);
        if(constraintViolations.size() > 0){
            List<String> errorMessages = new ArrayList<>();
            for(ConstraintViolation<UserLiztube> constraintViolation : constraintViolations){
                errorMessages.add(constraintViolation.getMessage());
            }
            throw new UserException("Not valid format entity", errorMessages);
        }

        userLiztubeRepository.saveAndFlush(userLiztube);

        return "Password changed successful";
    }


    /**
     * Define if the an email is already registered
     * @param testExistFacade
     * @return
     */
    public Boolean existEmailUpdate(TestExistFacade testExistFacade) throws UserNotFoundException {
        UserLiztube userLiztube = authBusiness.getConnectedUser(true);
        if(userLiztubeRepository.countByEmail(testExistFacade.getValue()) == 0 && userLiztube.getEmail().equals("spywen@hotmail.fr")){
            return false;
        }
        return true;
    }

}
