package com.liztube.entity;

import javax.persistence.*;

/**
 * Created by laurent on 18/03/2016.
 */
@Entity
@Table(name="PROJ_EMP")
@IdClass(ProjectAssociationId.class)
public class ProjectAssociation {
    @Id
    @Column(name = "employee_id")
    private long employeeId;
    @Id
    @Column(name = "project_id")
    private long projectId;
    @Column(name="IS_PROJECT_LEAD")
    private boolean isProjectLead;

    @ManyToOne
    @PrimaryKeyJoinColumn(referencedColumnName="ID")
  /* if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "employeeId", updatable = false, insertable = false)
  * or @JoinColumn(name = "employeeId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    private Employee employee;
    @ManyToOne
    @PrimaryKeyJoinColumn(referencedColumnName="ID")
  /* the same goes here:
  *  if this JPA model doesn't create a table for the "PROJ_EMP" entity,
  *  please comment out the @PrimaryKeyJoinColumn, and use the ff:
  *  @JoinColumn(name = "projectId", updatable = false, insertable = false)
  * or @JoinColumn(name = "projectId", updatable = false, insertable = false, referencedColumnName = "id")
  */
    private Project project;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public boolean isProjectLead() {
        return isProjectLead;
    }

    public void setProjectLead(boolean projectLead) {
        isProjectLead = projectLead;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
