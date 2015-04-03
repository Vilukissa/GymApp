package com.calicode.gymapp.app.util;

import com.calicode.gymapp.app.Config;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

import java.security.MessageDigest;

public class Encryption {

    public static String md5(String str) {
        StringBuilder builder = new StringBuilder();
        try {
            byte[] bytesOfMessage = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            for (byte aThedigest : thedigest) {
                builder.append(Integer.toHexString((
                        aThedigest & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (Exception ex) {
            Log.error("Creating MD5 hash failed", ex);
        }
        return builder.toString();
    }

    public static String generateSharedKey() {
        return md5("elephant_mother_and_father");
    }

    public static String generateLoginToken(String password) {
        String authToken = ComponentProvider.get().getComponent(UserSessionManager.class)
                .getAuthenticationData().getAuthToken();
        return md5(Config.NOAHS_ARK + authToken + password);
    }
}
