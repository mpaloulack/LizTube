package com.liztube.exception.exceptionType;

import com.liztube.exception.SigninException;

import java.util.List;

/**
 * Created by laurent on 22/02/15.
 */
public class ExceptionForControllerAdvice {

    //region attributes
    private String code = "#0";
    private String globalDescriptor;
    private List<String> messages;
    //endregion

    //region exceptions manage by the controller advice
    public ExceptionForControllerAdvice(SigninException signinException){
        this.code = signinException.getCode();
        this.globalDescriptor = signinException.getGlobalDescriptor();
        this.messages = signinException.getMessages();
    }
    //endregion

    //region getter/setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGlobalDescriptor() {
        return globalDescriptor;
    }

    public void setGlobalDescriptor(String globalDescriptor) {
        this.globalDescriptor = globalDescriptor;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    //endregion
}
