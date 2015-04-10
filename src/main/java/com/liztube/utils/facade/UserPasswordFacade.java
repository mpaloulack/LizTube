package com.liztube.utils.facade;

/**
 * Created by maxime on 07/04/2015.
 */
public class UserPasswordFacade {

    //Region attribute
    private String oldPassword;
    private String newPassword;
    //endregion

    //getter and setter


    public String getOldPassword() {
        return oldPassword;
    }

    public UserPasswordFacade setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserPasswordFacade setNewPassword(String newPassword) {
        this.newPassword = newPassword;return this;
    }
}
