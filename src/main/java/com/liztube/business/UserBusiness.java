package com.liztube.business;

import com.google.common.base.Strings;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.utils.EnumError;
import com.liztube.utils.facade.SigninTestExistFacade;
import com.liztube.utils.facade.UserForRegistration;
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
    public UserForRegistration getUserInfo() throws UserNotFoundException {
        UserLiztube userLiztube = authBusiness.getConnectedUser();

        UserForRegistration userInfo = new UserForRegistration()
                .setEmail(userLiztube.getEmail())
                .setFirstname(userLiztube.getFirstname())
                .setLastname(userLiztube.getLastname())
                .setBirthdate(userLiztube.getBirthdate());

        return userInfo;
    }

    /**
     * PUT user information
     * @return userInfo
     * @throws com.liztube.exception.UserNotFoundException
     */
    public UserLiztube updateUserInfo(UserForRegistration userInfo) throws UserNotFoundException, UserException {
        UserLiztube userLiztube = authBusiness.getConnectedUser();

        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);

        //update user persistent
        userLiztube
                .setFirstname(userInfo.getFirstname())
                .setLastname(userInfo.getLastname())
                .setBirthdate(userInfo.getBirthdate())
                .setModificationdate(Timestamp.valueOf(LocalDateTime.now()));


        //verify email if modify
        if (!userInfo.getEmail().equals(userLiztube.getEmail())){
            System.out.println("email non égale");
            if(authBusiness.existEmail(new SigninTestExistFacade().setValue(userInfo.getEmail())) || authBusiness.existPseudo(new SigninTestExistFacade().setValue(userInfo.getPseudo()))){
                List<String> errorMessages = new ArrayList<>();
                errorMessages.add(EnumError.SIGNIN_EMAIL_OR_PSEUDO_ALREADY_USED);
                throw new UserException("email already exist", errorMessages);
            }
            userLiztube.setEmail(userInfo.getEmail());
        }

        //Password changed
        if(!Strings.isNullOrEmpty(userInfo.getPassword())){
            System.out.println("password is null or empty");
            //Password well formatted
            if(!userInfo.getPassword().matches(authBusiness.PASSWORD_REGEX)){
                throw new UserException("Not valid format for password", EnumError.SIGNIN_PASSWORD_FORMAT);
            }
            userLiztube.setPassword(encoder.encodePassword(userInfo.getPassword(), null));
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
}
