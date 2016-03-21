package com.liztube.repository;

import com.liztube.entity.Project;
import com.liztube.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project repository : data access layer to the projects
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
