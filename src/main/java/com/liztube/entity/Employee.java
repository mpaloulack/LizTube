package com.liztube.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurent on 18/03/2016.
 */
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "employeeId")
    @SequenceGenerator(name = "employeeId", sequenceName = "EMPLOYEEID")
    private long id;

    @OneToMany(mappedBy="employee")
    private List<ProjectAssociation> projects = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProjectAssociation> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectAssociation> projects) {
        this.projects = projects;
    }
}
