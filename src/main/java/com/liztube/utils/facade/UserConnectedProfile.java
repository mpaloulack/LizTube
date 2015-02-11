package com.liztube.utils.facade;

import java.util.List;

/**
 * Facade with connected user data
 */
public class UserConnectedProfile {

    //region attributs
    private String pseudo;
    private List<String> roles;
    //endregion

    //region constructor
    public UserConnectedProfile(String pseudo, List<String> roles){
        this.pseudo = pseudo;
        this.roles = roles;
    }
    //endregion

    //region getter/setter
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    //endregion

}
