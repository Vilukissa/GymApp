package com.calicode.gymapp.app.model.logout;

import com.android.volley.Request.Method;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonParser;

public class LogoutOperation extends JsonOperation {

    public LogoutOperation() {
        super(Method.POST, new JsonParser(Object.class));
    }

    @Override
    public String getUrl() {
        return "/v1/logout";
    }
}
