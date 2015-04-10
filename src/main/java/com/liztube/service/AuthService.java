package com.liztube.service;

import com.liztube.business.AuthBusiness;
import com.liztube.exception.SigninException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.utils.facade.TestExistFacade;
import com.liztube.utils.facade.UserConnectedProfile;
import com.liztube.utils.facade.UserForRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Service which provide all the method concerning the authentication of an user (login, signin, get user profile...)
 */
@RestController
@RequestMapping("/api/auth")
public class AuthService {
    @Autowired
    AuthBusiness authBusiness;

    /**
     * Get current user profile
     * @return
     * @throws UserNotFoundException
     */
    @RequestMapping(value = "/currentProfil",method = RequestMethod.GET)
    public UserConnectedProfile getUserProfil() {
        return authBusiness.getUserConnectedProfile(true);
    }

    /**
     * Register to the liztube service as a user
     * @param userForRegistration
     * @return
     * @throws SigninException
     */
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public long signIn(@RequestBody UserForRegistration userForRegistration) throws SigninException {
        return authBusiness.signIn(userForRegistration).getId();
    }

    /**
     * Determine if a pseudo is already used
     * @param testExistFacade
     * @return
     */
    @RequestMapping(value = "/pseudo", method = RequestMethod.POST)
    public Boolean existPseudo(@RequestBody TestExistFacade testExistFacade){
        return authBusiness.existPseudo(testExistFacade);
    }

    /**
     * Determine if an email is already used
     * @param testExistFacade
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public Boolean existEmail(@RequestBody TestExistFacade testExistFacade){
        return authBusiness.existEmail(testExistFacade);
    }


}
