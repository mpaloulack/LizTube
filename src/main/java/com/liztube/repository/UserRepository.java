package com.liztube.repository;

import com.liztube.entity.UserLiztube;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by laurent on 11/02/15.
 */
public interface UserRepository extends JpaRepository<UserLiztube, Long> {
    public UserLiztube findByEmailOrPseudo(String email, String pseudo);
}