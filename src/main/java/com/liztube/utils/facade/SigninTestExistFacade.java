package com.liztube.utils.facade;

/**
 * Facade to give test if a pseudo or an email are already used
 */
public class SigninTestExistFacade {
    private String value;

    public String getValue() {
        return value;
    }

    public SigninTestExistFacade setValue(String value) {
        this.value = value; return this;
    }
}
