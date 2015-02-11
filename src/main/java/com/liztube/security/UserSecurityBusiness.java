package com.liztube.security;

import com.liztube.entity.UserLiztube;
import com.liztube.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by laurent on 19/07/2014.
 */
@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserSecurityBusiness implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserLiztube domainUser = userRepository.findByEmailOrPseudo(username, username);

            return new User(
                    domainUser.getEmail(),
                    domainUser.getPassword(),
                    domainUser.getIsactive(),
                    true,//domainUser.isAccountNonExpired(),
                    true,//domainUser.isCredentialsNonExpired(),
                    true,//domainUser.isAccountNonLocked(),
                    domainUser.getRolesAutorithies()
            );

        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
