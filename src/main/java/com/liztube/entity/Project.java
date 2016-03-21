package com.liztube.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurent on 18/03/2016.
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "projectId")
    @SequenceGenerator(name = "projectId", sequenceName = "PROJECTID")
    private long id;

    @OneToMany(mappedBy="project", fetch = FetchType.EAGER)
    private List<ProjectAssociation> employees = new ArrayList<>();

    // Add an employee to the project.
    // Create an association object for the relationship and set its data.
    public void addEmployee(Employee employee, boolean teamLead) {
        ProjectAssociation association = new ProjectAssociation();
        association.setEmployee(employee);
        association.setProject(this);
        association.setProjectLead(teamLead);

        this.employees.add(association);
        // Also add the association object to the employee.
        employee.getProjects().add(association);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProjectAssociation> getEmployees() {
        return employees;
    }

    public void setEmployees(List<ProjectAssociation> employees) {
        this.employees = employees;
    }
}
