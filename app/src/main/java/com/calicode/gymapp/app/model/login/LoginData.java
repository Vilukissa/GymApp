package com.calicode.gymapp.app.model.login;

import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginData {

    @JsonProperty("lastVisit")
    private String mLastVisit;

    @JsonCreator
    private LoginData(@JsonProperty("sid") String sessionId) {
        if (sessionId != null) {
            Log.debug("Setting auth token");
            ComponentProvider.get().getComponent(UserSessionManager.class).setAuthToken(sessionId);
        }
    }

    public String getLastVisit() {
        return mLastVisit;
    }
}
