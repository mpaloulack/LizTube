package com.liztube.repository;

import com.liztube.entity.Employee;
import com.liztube.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project repository : data access layer to the projects
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
