package com.calicode.gymapp.app.util;

import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

import java.security.MessageDigest;

public class Encryption {

    public static String md5(String str) {
        StringBuffer sb = new StringBuffer();
        try {
            byte[] bytesOfMessage = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            for (int i = 0; i < thedigest.length; ++i) {
                sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1,3));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public static String generateSharedKey() {
        return "elephant_mother_and_father";
    }

    public static String generateLoginToken(String username, String password) {
        String authToken = ComponentProvider.get().getComponent(UserSessionManager.class)
                .getAuthenticationData().getAuthToken();
        String loginToken = md5(username + authToken + password);
        return loginToken;
    }
}
