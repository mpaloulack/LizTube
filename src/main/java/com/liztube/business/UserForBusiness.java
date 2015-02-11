package com.liztube.business;

import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserNotFoundException;
import com.liztube.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * Class only for business. Not use by services
 */
@Component
class UserForBusiness {

    @Autowired
    public UserRepository userRepository;

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
                String name = user.getUsername();
                return userRepository.findByPseudo(name);
            }catch (Exception e){
                throw new UserNotFoundException("NotConnected","user not connected");
            }
        }
        throw new UserNotFoundException("NotConnected","user not connected");
    }

}
