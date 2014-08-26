package com.calicode.gymapp.app.model.authentication.operation;

import com.calicode.gymapp.app.model.authentication.data.AuthenticationData;
import com.calicode.gymapp.app.network.BaseParser;

import org.json.JSONObject;

public class AuthenticationParser extends BaseParser {

    @Override
    public Object parseObject(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        AuthenticationData data = new AuthenticationData();
        // TODO: parsing...
        return data;
    }
}
