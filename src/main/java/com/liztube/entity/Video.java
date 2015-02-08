package com.liztube.entity;

import javax.persistence.*;

/**
 * Created by laurent on 07/02/15.
 */
@Entity
@Table(name = "VIDEO")
public class Video {

    //region attributes
    private String key;
    private String title;
    private String description;
    private UserLiztube owner;
    private Boolean ispublic;
    private Boolean ispubliclink;
    //endregion

    //region getter/setter
    @Id
    @Column(name = "KEYID")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Lob
    @Column(name = "TITLE", nullable = false, insertable = true, updatable = true, length = 300)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Lob
    @Column(name = "DESCRIPTION", nullable = true, insertable = true, updatable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name="USER", nullable=false, referencedColumnName = "ID")
    public UserLiztube getOwner() {
        return owner;
    }

    public void setOwner(UserLiztube owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "ISPUBLIC", nullable = false, insertable = true, updatable = true)
    public Boolean getIspublic() {
        return ispublic;
    }

    public void setIspublic(Boolean ispublic) {
        this.ispublic = ispublic;
    }

    @Basic
    @Column(name = "ISPUBLICLINK", nullable = false, insertable = true, updatable = true)
    public Boolean getIspubliclink() {
        return ispubliclink;
    }

    public void setIspubliclink(Boolean ispubliclink) {
        this.ispubliclink = ispubliclink;
    }
    //endregion

    //region override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (description != null ? !description.equals(video.description) : video.description != null) return false;
        if (ispublic != null ? !ispublic.equals(video.ispublic) : video.ispublic != null) return false;
        if (ispubliclink != null ? !ispubliclink.equals(video.ispubliclink) : video.ispubliclink != null) return false;
        if (key != null ? !key.equals(video.key) : video.key != null) return false;
        if (owner != null ? !owner.equals(video.owner) : video.owner != null) return false;
        if (title != null ? !title.equals(video.title) : video.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (ispublic != null ? ispublic.hashCode() : 0);
        result = 31 * result + (ispubliclink != null ? ispubliclink.hashCode() : 0);
        return result;
    }
    //endregion

}
