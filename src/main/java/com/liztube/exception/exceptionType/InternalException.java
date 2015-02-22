package com.liztube.exception.exceptionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurent on 22/02/15.
 */
public class InternalException extends Exception {
    private String code = "#0";
    private String globalDescriptor;

    public InternalException() { super(); }

    public InternalException(String code, String globalDescriptor){
        super(globalDescriptor);
        this.code = code;
        this.globalDescriptor = globalDescriptor;
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
}
