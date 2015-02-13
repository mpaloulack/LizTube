package com.liztube.business;

import com.liztube.entity.Role;
import com.liztube.entity.UserLiztube;
import com.liztube.exception.UserNotFoundException;
import com.liztube.repository.RoleRepository;
import com.liztube.repository.UserLiztubeRepository;
import com.liztube.repository.predicate.UserLiztubePredicates;
import com.liztube.utils.facade.SignInTestExistFacade;
import com.liztube.utils.facade.UserConnectedProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import sun.misc.MessageUtils;

import java.util.*;

/**
 * Class which manage all the method concerning the authentication of an user
 */
@Component
public class AuthBusiness {
    @Autowired
    public UserLiztubeRepository userLiztubeRepository;

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
     * @param signInTestExistFacade
     * @return
     */
    public Boolean existEmail(SignInTestExistFacade signInTestExistFacade){
        if(userLiztubeRepository.countByEmail(signInTestExistFacade.getValue()) == 0){
            return false;
        }
        return true;
    }

    /**
     * Define if the a pseudo is already registered
     * @param signInTestExistFacade
     * @return
     */
    public Boolean existPseudo(SignInTestExistFacade signInTestExistFacade){
        if(userLiztubeRepository.countByPseudo(signInTestExistFacade.getValue()) == 0){
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
                String name = user.getUsername();
                return userLiztubeRepository.findByEmailOrPseudo(name, name);
            }catch (Exception e){
                throw new UserNotFoundException("NotConnected","user not connected");
            }
        }
        throw new UserNotFoundException("NotConnected","user not connected");
    }
}
