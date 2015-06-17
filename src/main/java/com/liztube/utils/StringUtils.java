package com.liztube.utils;

import sun.misc.BASE64Decoder;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * String Utils
 */
@Component
public class StringUtils {
    /**
     * Decode string url encoded
     * See : http://www.url-encode-decode.com/
     * @param str
     * @return
     */
    public static String UrlDecoder(String str){
        try {
            return URLDecoder.decode(str, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64 -> UTF-8
     * @param value
     * @return
     */
    public static String Base64Decode(String value){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(value);
            String finalValue = new String(decodedBytes, "UTF-8");
            return finalValue;
        } catch (IOException e) {
            e.printStackTrace();
            return value;
        }
    }

    /**
     * UTF-8 -> Base64
     * @param value
     * @return
     */
    public static String Base64Encode(String value){
        BASE64Encoder encoder = new BASE64Encoder();
        String decodedBytes = encoder.encode(value.getBytes());
        return decodedBytes;
    }
}
