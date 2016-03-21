package com.liztube.entity;

import java.io.Serializable;

/**
 * Created by laurent on 18/03/2016.
 */
public class ProjectAssociationId implements Serializable {

    private long employee;

    private long project;

    public int hashCode() {
        return (int)(employee + project);
    }

    public boolean equals(Object object) {
        if (object instanceof ProjectAssociationId) {
            ProjectAssociationId otherId = (ProjectAssociationId) object;
            return (otherId.employee == this.employee) && (otherId.project == this.project);
        }
        return false;
    }

}
