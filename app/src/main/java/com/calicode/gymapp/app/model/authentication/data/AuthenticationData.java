package com.calicode.gymapp.app.model.authentication.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationData {

    @JsonProperty("authToken")
    private String mAuthToken;

    public String getAuthToken() {
        return mAuthToken;
    }
}
