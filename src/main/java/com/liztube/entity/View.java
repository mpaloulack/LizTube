package com.liztube.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by laurent on 07/02/15.
 */
@Entity
@Table(name = "VIEW")
public class View {

    //region attributes
    private long id;
    private UserLiztube user;
    private Video video;
    private Timestamp viewdate;
    //endregion

    //region getter/setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "viewId")
    @SequenceGenerator(name = "viewId", sequenceName = "VIEWID")
    @Column(name = "ID", nullable = false, insertable = true, updatable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="USER", nullable=false, updatable = false, referencedColumnName = "ID")
    public UserLiztube getUser() {
        return user;
    }

    public void setUser(UserLiztube user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="VIDEO", nullable=false, updatable = false, referencedColumnName = "KEYID")
    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @JsonIgnore
    @Basic
    @Column(name = "VIEWDATE", nullable = false, insertable = true, updatable = false)
    public Timestamp getViewdate() {
        return viewdate;
    }

    public void setViewdate(Timestamp viewdate) {
        this.viewdate = viewdate;
    }
    //endregion

    //region override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        View view = (View) o;

        if (id != view.id) return false;
        if (user != null ? !user.equals(view.user) : view.user != null) return false;
        if (video != null ? !video.equals(view.video) : view.video != null) return false;
        if (viewdate != null ? !viewdate.equals(view.viewdate) : view.viewdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (video != null ? video.hashCode() : 0);
        result = 31 * result + (viewdate != null ? viewdate.hashCode() : 0);
        return result;
    }
    //endregion

}
