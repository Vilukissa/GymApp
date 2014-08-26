package com.calicode.gymapp.app.model.authentication.operation;

import com.calicode.gymapp.app.model.authentication.data.AuthenticationData;
import com.calicode.gymapp.app.network.BaseParser;
import com.fasterxml.jackson.databind.ObjectReader;

public class AuthenticationParser extends BaseParser {

    @Override
    public Object parseObject(ObjectReader objectReader, String json) throws Exception {
        return objectReader.withType(AuthenticationData.class).readValue(json);
    }
}
