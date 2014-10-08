package com.calicode.gymapp.app.model.login;

import com.android.volley.Request;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonParser;
import com.calicode.gymapp.app.util.Encryption;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

import java.util.HashMap;
import java.util.Map;

public class LoginOperation extends JsonOperation {

    private final String mUserName;
    private final String mPassword;
    private final String mAuthToken;

    public LoginOperation(String userName, String password) {
        super(Request.Method.POST, new JsonParser(LoginData.class));
        mUserName = userName;
        mPassword = password;
        mAuthToken = ComponentProvider.get().getComponent(UserSessionManager.class)
                .getAuthenticationData().getAuthToken();
    }

    @Override
    public String getUrl() {
        return "/v1/login";
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", mUserName);
        params.put("password", mPassword);
        params.put("authToken", Encryption.generateLoginToken(mUserName, mPassword));
        params.put("key", mAuthToken);
        return params;
    }
}
