package com.liztube.exception;

import com.liztube.exception.exceptionType.PublicException;

import java.util.List;

/**
 * Created by laurent on 15/02/15.
 */
public class SigninException extends PublicException {
    public SigninException(String code, String globalDescriptor, List<String> messages) {
        super(code, globalDescriptor, messages);
    }
}
