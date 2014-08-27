package com.calicode.gymapp.app.model.login;

import com.android.volley.Request;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class LoginOperation extends JsonOperation {

    public LoginOperation() {
        super(Request.Method.POST, new JsonParser(LoginData.class));
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        return params;
    }
}
