package com.liztube.exception;

import com.liztube.exception.exceptionType.InternalException;
import com.liztube.utils.EnumError;

/**
 * User not found exception : if a user is not authenticated according to their login informations
 */
public class UserNotFoundException extends InternalException {
    public UserNotFoundException(String code) {
        super(code, EnumError.USER_NOT_FOUND_EXCEPTION);
    }
}
