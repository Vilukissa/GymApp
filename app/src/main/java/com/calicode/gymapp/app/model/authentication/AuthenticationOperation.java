package com.calicode.gymapp.app.model.authentication;

import com.android.volley.Request;
import com.calicode.gymapp.app.Config;
import com.calicode.gymapp.app.network.JsonParser;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.Encryption;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationOperation extends JsonOperation {

    private static final String AUTH_TOKEN_KEY = "authToken";
    private static final String SHARED_KEY = "key";

    public AuthenticationOperation() {
        super(Request.Method.POST, new JsonParser(AuthenticationData.class));
    }

    @Override
    public String getUrl() {
        return "/v1/auth";
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();

        // TODO: move these away
        // FIXME: very primitive implementation for the first test runs...
        String privateKey = Encryption.md5(Config.NOAHS_ARK);
        String sharedKey = Encryption.generateSharedKey();
        String token = Encryption.md5(privateKey + sharedKey);

        params.put(AUTH_TOKEN_KEY, token);
        params.put(SHARED_KEY, sharedKey);

        return params;
    }
}
