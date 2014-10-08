package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.model.authentication.AuthenticationData;
import com.calicode.gymapp.app.model.login.LoginData;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UserSessionManager implements SessionComponent {

    public interface OnLoginStateChangedListener {
        void loginStateChanged(LoginState loginState);
    }

    public enum LoginState {
        NOT_AUTHENTICATED,
        AUTHENTICATED,
        LOGGED_IN,
        LOGGED_OUT
    }

    private Map<Class, OnLoginStateChangedListener> mLoginStateListeners = new HashMap<Class, OnLoginStateChangedListener>();
    private AuthenticationData mAuthenticationData;
    private LoginData mLoginData;
    private LoginState mLoginState = LoginState.NOT_AUTHENTICATED;

    public LoginState getLoginState() {
        return mLoginState;
    }

    public void authenticated() {
        mLoginState = LoginState.AUTHENTICATED;
        notifyListeners();
    }

    public void setAuthenticationData(AuthenticationData authToken) {
        mAuthenticationData = authToken;
    }

    public AuthenticationData getAuthenticationData() {
        return mAuthenticationData;
    }

    public String getTokenForCookies() {
        if (mLoginState == LoginState.AUTHENTICATED) {
            // TODO: this is never used because auth request
            // is blacklisted in VolleyHandler.java
            return mAuthenticationData.getAuthToken();
        } else if (mLoginState == LoginState.LOGGED_IN) {
            return mLoginData.getSessionId();
        } else {
            return "";
        }
    }

    public void loggedIn() {
        mLoginState = LoginState.LOGGED_IN;
        notifyListeners();
    }

    public LoginData getLoginData() {
        return mLoginData;
    }

    public void setLoginData(LoginData loginData) {
        mLoginData = loginData;
    }

    public void loggedOut() {
        mLoginState = LoginState.LOGGED_OUT;
        notifyListeners();
    }

    public void addOnLoginStateChangedListener(OnLoginStateChangedListener listener) {
        mLoginStateListeners.put(listener.getClass(), listener);
        listener.loginStateChanged(mLoginState);
    }

    public void removeOnLoginStateChangedListener(OnLoginStateChangedListener listener) {
        mLoginStateListeners.remove(listener.getClass());
    }

    private void notifyListeners() {
        for (Entry<Class, OnLoginStateChangedListener> listenerEntry : mLoginStateListeners.entrySet()) {
            listenerEntry.getValue().loginStateChanged(mLoginState);
        }
    }

    @Override
    public void destroy() {
        mAuthenticationData = null;
        mLoginData = null;
        mLoginState = LoginState.NOT_AUTHENTICATED;
        mLoginStateListeners.clear();
    }
}
