package com.liztube.repository;

import com.liztube.entity.UserLiztube;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository : data access layer to the users
 */
public interface UserRepository extends JpaRepository<UserLiztube, Long> {
    /**
     * Find a user according to his email or his pseudo
     * @param email
     * @param pseudo
     * @return
     */
    public UserLiztube findByEmailOrPseudo(String email, String pseudo);

    /**
     * Find a user according to his pseudo
     * @param pseudo
     * @return
     */
    public UserLiztube findByPseudo(String pseudo);

    /**
     * Return the count of user(s) with the same email
     * @param email
     * @return
     */
    long countByEmail(String email);

    /**
     * Return the count of user(s) with the same pseudo
     * @param pseudo
     * @return
     */
    long countByPseudo(String pseudo);
}