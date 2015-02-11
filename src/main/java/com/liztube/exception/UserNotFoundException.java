package com.liztube.exception;

/**
 * User not found exception : if a user is not authenticated according to their login informations
 */
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String type, String message) {
        super(type, message);
    }
}
