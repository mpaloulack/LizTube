package com.liztube.utils.facade;

/**
 * Facade to give test if a pseudo or an email are already used
 */
public class SignInTestExistFacade {
    private String value;

    public String getValue() {
        return value;
    }

    public SignInTestExistFacade setValue(String value) {
        this.value = value; return this;
    }
}
