package com.liztube.exception.exceptionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class exception
 */
public class PublicException extends Exception {
    private String code = "#0";
    private String globalDescriptor;
    private List<String> messages;

    public PublicException() { super(); }

    public PublicException(String code, String globalDescriptor){
        super(globalDescriptor);
        this.code = code;
        this.globalDescriptor = globalDescriptor;
        this.messages = new ArrayList<>();
    }

    public PublicException(String code, String globalDescriptor, List<String> messages){
        super(globalDescriptor);
        this.code = code;
        this.globalDescriptor = globalDescriptor;
        this.messages = messages;
    }

    public String getGlobalDescriptor() {
        return globalDescriptor;
    }

    public void setGlobalDescriptor(String globalDescriptor) {
        this.globalDescriptor = globalDescriptor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
