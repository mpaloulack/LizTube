package com.liztube.exception.exceptionType;

import com.liztube.exception.SigninException;
import com.liztube.exception.VideoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class exception
 */
public class PublicException extends InternalException {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //region attributes
    private List<String> messages = new ArrayList<>();
    //endregion

    //region Constructor of the customize base exception
    public PublicException(String log){

        super.log(log);
        addMessage("An unexpected error occured. If the problem persists please contact the administrator.");
        logException();
    }

    public PublicException(String log, List<String> messages) {
        super.log(log);
        this.messages = messages;
        logException();
    }

    public PublicException(String log, String message) {
        super.log(log);
        this.addMessage(message);
        logException();
    }
    //endregion

    //region Constructors for exceptions manage by the controller advice
    public PublicException(SigninException signinException){
        this.messages = signinException.getMessages();
    }

    public PublicException(VideoException videoException){
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
    public void addMessage(String message){
        this.messages.add(message);
    }
    //endregion

    private void logException(){
        logger.error(super.log(), this.messages, this.getClass(), this.getStackTrace(), this.getCause());
    }

}
