package com.liztube.service;

import com.liztube.exception.SigninException;
import com.liztube.exception.VideoException;
import com.liztube.exception.exceptionType.PublicException;
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
    public PublicException signInException(SigninException signinException){
        return new PublicException(signinException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(VideoException.class)
    @ResponseBody
    public PublicException videoException(VideoException videoException){
        return new PublicException(videoException);
    }

}
