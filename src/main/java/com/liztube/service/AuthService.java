package com.liztube.service;

import com.liztube.business.AuthBusiness;
import com.liztube.exception.UserNotFoundException;
import com.liztube.utils.facade.SignInTestExistFacade;
import com.liztube.utils.facade.UserConnectedProfile;
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
        return authBusiness.getUserConnectedProfile();
    }

    /**
     * Determine if a pseudo is already used
     * @param signInTestExistFacade
     * @return
     */
    @RequestMapping(value = "/pseudo", method = RequestMethod.POST)
    public Boolean existPseudo(@RequestBody SignInTestExistFacade signInTestExistFacade){
        return authBusiness.existPseudo(signInTestExistFacade);
    }

    /**
     * Determine if an email is already used
     * @param signInTestExistFacade
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public Boolean existEmail(@RequestBody SignInTestExistFacade signInTestExistFacade){
        return authBusiness.existEmail(signInTestExistFacade);
    }
}
