package com.liztube.business;

import com.liztube.entity.Role;
import com.liztube.entity.UserLiztube;
import com.liztube.repository.RoleRepository;
import com.liztube.repository.UserRepository;
import com.liztube.utils.facade.SignInTestExistFacade;
import com.liztube.utils.facade.UserConnectedProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Class which manage all the method concerning the authentication of an user
 */
@Component
public class AuthBusiness {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserForBusiness userForBusiness;

    /**
     * Get partial user profile : Username (login) and roles
     * @return
     */
    public UserConnectedProfile getUserConnectedProfile(){
        List<String> roles = new ArrayList<String>();
        try{
            UserLiztube user = userForBusiness.getConnectedUser();
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
        if(userRepository.countByEmail(signInTestExistFacade.getValue()) == 0){
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
        if(userRepository.countByPseudo(signInTestExistFacade.getValue()) == 0){
            return false;
        }
        return true;
    }
}