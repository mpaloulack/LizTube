package com.liztube.service;

import com.liztube.entity.Video;
import com.liztube.exception.VideoException;
import com.liztube.exception.exceptionType.ExceptionForControllerAdvice;
import com.liztube.exception.SigninException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controller advice. In case of an exception occured : a clean message will be send through the api.
 */
@ControllerAdvice
public class LiztubeControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SigninException.class)
    @ResponseBody
    public ExceptionForControllerAdvice signInException(SigninException signinException){
        return new ExceptionForControllerAdvice(signinException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VideoException.class)
    @ResponseBody
    public ExceptionForControllerAdvice videoException(VideoException videoException){
        return new ExceptionForControllerAdvice(videoException);
    }



}
