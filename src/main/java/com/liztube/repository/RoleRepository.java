package com.liztube.repository;

import com.liztube.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Role repository : data access layer to the roles
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find a role according to his name
     * @param name
     * @return
     */
    Role findByName(String name);
}
