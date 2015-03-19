package com.liztube.service;

import com.liztube.business.UserBusiness;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.SigninException;
import com.liztube.exception.UserException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.utils.facade.UserForRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service which provide all the method concerning the user  (infos user, modification...)
 */
@RestController
@RequestMapping("/api/user")
public class UserService {

    @Autowired
    UserBusiness userBusiness;

    /**
     * Get current user profile infos
     * @return
     * @throws com.liztube.exception.UserNotFoundException
     */
    @RequestMapping(value = "/infoProfile",method = RequestMethod.GET)
    public UserForRegistration getUserInfoProfile()  throws UserNotFoundException {
        return userBusiness.getUserInfo();
    }

    /**
     * Put infos user profile
     * @return
     * @throws UserNotFoundException
     */
    @RequestMapping(value = "/infoProfile",method = RequestMethod.POST)
    public UserLiztube putUserInfo(@RequestBody UserForRegistration userInfo) throws UserNotFoundException, UserException {
        return userBusiness.updateUserInfo(userInfo);
    }
}
