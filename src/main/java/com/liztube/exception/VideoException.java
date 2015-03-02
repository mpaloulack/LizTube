package com.liztube.exception;

import com.liztube.exception.exceptionType.PublicException;

import java.util.List;

/**
 * Video exception
 */
public class VideoException extends PublicException {
    public VideoException(String code, String globalDescriptor, List<String> messages) {
        super(code, globalDescriptor, messages);
    }
}
