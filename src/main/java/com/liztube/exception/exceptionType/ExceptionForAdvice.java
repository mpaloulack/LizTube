package com.liztube.exception.exceptionType;

import com.liztube.exception.SigninException;
import com.liztube.exception.UserNotFoundException;
import com.liztube.exception.VideoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception for controller advice (message of the exception to send to the client)
 */
public class ExceptionForAdvice{

    //region attributes
    private List<String> messages = new ArrayList<>();
    //endregion

    //region Constructors for exceptions manage by the controller advice
    public ExceptionForAdvice(UserNotFoundException userNotFoundException){
        this.messages = userNotFoundException.getMessages();
    }

    public ExceptionForAdvice(SigninException signinException){
        this.messages = signinException.getMessages();
    }

    public ExceptionForAdvice(VideoException videoException){
        this.messages = videoException.getMessages();
    }
    //endregion

    //region getter/setter

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    //endregion

}
