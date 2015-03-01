package com.liztube.repository;

import com.liztube.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access layer to videos
 */
public interface VideoRepository extends JpaRepository<Role, Long> {
}
