package com.liztube.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by laurent on 07/02/15.
 */
@Entity
@Table(name = "USER")
public class UserLiztube {

    //region attributes
    private long id;
    private String firstname;
    private String lastname;
    private String pseudo;
    private String password;
    private Timestamp birthdate;
    private String email;
    private Timestamp registerdate;
    private Timestamp modificationdate;
    private Boolean isfemale;
    private Boolean isactive;
    private Set<Role> roles;
    //endregion

    //region getter/setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "userId")
    @SequenceGenerator(name = "userId", sequenceName = "USERID")
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    @Basic
    @Column(name = "FIRSTNAME", nullable = false, insertable = true, updatable = true, length = 100)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonIgnore
    @Basic
    @Column(name = "LASTNAME", nullable = false, insertable = true, updatable = true, length = 100)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "PSEUDO", nullable = false, insertable = true, updatable = true, length = 50)
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @JsonIgnore
    @Basic
    @Column(name = "PASSWORD", nullable = false, insertable = true, updatable = true, length = 200)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    @Basic
    @Column(name = "BIRTHDATE", nullable = false, insertable = true, updatable = true)
    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    @JsonIgnore
    @Basic
    @Column(name = "EMAIL", nullable = false, insertable = true, updatable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    @Basic
    @Column(name = "REGISTERDATE", nullable = false, insertable = true, updatable = true)
    public Timestamp getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Timestamp registerdate) {
        this.registerdate = registerdate;
    }

    @JsonIgnore
    @Basic
    @Column(name = "MODIFICATIONDATE", nullable = true, insertable = true, updatable = true)
    public Timestamp getModificationdate() {
        return modificationdate;
    }

    public void setModificationdate(Timestamp modificationdate) {
        this.modificationdate = modificationdate;
    }

    @JsonIgnore
    @Basic
    @Column(name = "ISFEMALE", nullable = false, insertable = true, updatable = true)
    public Boolean getIsfemale() {
        return isfemale;
    }

    public void setIsfemale(Boolean isfemale) {
        this.isfemale = isfemale;
    }

    @JsonIgnore
    @Basic
    @Column(name = "ISACTIVE", nullable = false, insertable = true, updatable = true)
    public Boolean getIsactive() {
        return isactive;
    }

    public UserLiztube setIsactive(Boolean isactive) {
        this.isactive = isactive;return this;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, updatable = false)})
    public Set<Role> getRoles() {
        return roles;
    }

    public UserLiztube setRoles(Set<Role> roles) {
        this.roles = roles; return this;
    }

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    public List<GrantedAuthority> getRolesAutorithies() {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (this.roles != null && !this.roles.isEmpty())
            for (Role role : this.roles) {
                authList.add(new SimpleGrantedAuthority(role.getName()));
            }
        return authList;
    }
    //endregion

    //region override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLiztube that = (UserLiztube) o;

        if (id != that.id) return false;
        if (birthdate != null ? !birthdate.equals(that.birthdate) : that.birthdate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (isactive != null ? !isactive.equals(that.isactive) : that.isactive != null) return false;
        if (isfemale != null ? !isfemale.equals(that.isfemale) : that.isfemale != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (modificationdate != null ? !modificationdate.equals(that.modificationdate) : that.modificationdate != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (pseudo != null ? !pseudo.equals(that.pseudo) : that.pseudo != null) return false;
        if (registerdate != null ? !registerdate.equals(that.registerdate) : that.registerdate != null) return false;
        if (roles != null ? !roles.equals(that.roles) : that.roles != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (pseudo != null ? pseudo.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (registerdate != null ? registerdate.hashCode() : 0);
        result = 31 * result + (modificationdate != null ? modificationdate.hashCode() : 0);
        result = 31 * result + (isfemale != null ? isfemale.hashCode() : 0);
        result = 31 * result + (isactive != null ? isactive.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
    //endregion

}
