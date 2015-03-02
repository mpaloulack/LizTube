package com.liztube.utils;

/**
 * Created by laurent on 15/02/15.
 */
public class EnumError {
    //USER
    public static final String USER_ERRORS                   = "#1050";
    public static final String USER_NOT_FOUND_EXCEPTION     = "#1051";
    //SIGNIN
    public static final String SIGNIN_ERRORS                = "#1000";
    public static final String SIGNIN_FIRSTNAME_SIZE        = "#1001";
    public static final String SIGNIN_LASTNAME_SIZE         = "#1002";
    public static final String SIGNIN_PSEUDO_SIZE           = "#1003";
    public static final String SIGNIN_BIRTHDAY_NOTNULL      = "#1004";
    public static final String SIGNIN_BIRTHDAY_PAST_DATE    = "#1005";
    public static final String SIGNIN_EMAIL_FORMAT          = "#1006";
    public static final String SIGNIN_EMAIL_SIZE            = "#1007";
    public static final String SIGNIN_REGISTER_NOTNULL      = "#1008";
    public static final String SIGNIN_REGISTER_PAST_DATE    = "#1009";
    public static final String SIGNIN_MODIFICATION_NOTNULL  = "#1010";
    public static final String SIGNIN_ISFEMALE_NOTNULL      = "#1011";
    public static final String SIGNIN_ISACTIVE_NOTNULL      = "#1012";
    public static final String SIGNIN_EMAIL_OR_PSEUDO_ALREADY_USED      = "#1013";
    //VIDEO
    public static final String VIDEO_ERRORS                = "#1050";
    public static final String VIDEO_UPLOAD_FILE_EMPTY     = "File is empty.";
    public static final String VIDEO_UPLOAD_NO_VALID_TYPE  = "Not valid type of file uploaded.";
    public static final String VIDEO_UPLOAD_TOO_HEAVY      = "File size exceed {0} Mo.";
    public static final String VIDEO_TITLE_SIZE            = "#1051";
    public static final String VIDEO_DESCRIPTION_SIZE      = "#1052";
    public static final String VIDEO_UPLOAD_SAVE_FILE_ON_SERVER     = "#1053";

}
