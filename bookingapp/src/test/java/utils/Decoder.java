package utils;

import java.util.Base64;

public class Decoder {

    public static String decode(String encodedString) {
        byte[] decodedBytes;
        decodedBytes = Base64.getUrlDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static void main(String[] args) {
        String sensitiveString = "************* Pass Sensitive String here to encode *************";
        String encodedString = Base64.getUrlEncoder().encodeToString(sensitiveString.getBytes());
        System.out.println(encodedString);
    }

}
