package com.liztube.service;

import com.liztube.business.UserBusiness;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.utils.facade.TestExistFacade;
import com.liztube.utils.facade.UserFacade;
import com.liztube.utils.facade.UserPasswordFacade;
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
    @RequestMapping(method = RequestMethod.GET)
    public UserFacade getUserInfoProfile()  throws UserNotFoundException {
        return userBusiness.getUserInfo();
    }

    /**
     * Update user data
     * @return
     * @throws UserNotFoundException
     */
    @RequestMapping(method = RequestMethod.PUT)
    public UserLiztube updateUserInfo(@RequestBody UserFacade userInfo) throws UserNotFoundException, UserException {
        return userBusiness.updateUserInfo(userInfo);
    }

    /**
     * Update password
     * @param userPasswordFacade
     * @return
     * @throws UserNotFoundException
     * @throws UserException
     */
   @RequestMapping(value = "/password", method = RequestMethod.PATCH)
    public boolean changeUserPassword(@RequestBody UserPasswordFacade userPasswordFacade) throws UserNotFoundException, UserException {
        return userBusiness.changeUserPassword(userPasswordFacade);
    }
}
