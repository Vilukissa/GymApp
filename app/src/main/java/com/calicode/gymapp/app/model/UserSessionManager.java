package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.model.login.LoginData;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class UserSessionManager implements SessionComponent {

    private String mAuthToken;
    private LoginData mLoginData;

    public void setAuthToken(String authToken) {
        mAuthToken = authToken;
    }

    public String getAuthToken() {
        return mAuthToken;
    }

    public LoginData getLoginData() {
        return mLoginData;
    }

    public void setLoginData(LoginData loginData) {
        mLoginData = loginData;
    }

    @Override
    public void destroy() {
        mAuthToken = null;
        mLoginData = null;
    }
}
