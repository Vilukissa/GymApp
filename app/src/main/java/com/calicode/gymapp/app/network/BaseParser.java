package com.calicode.gymapp.app.network;

import android.text.TextUtils;

import com.calicode.gymapp.app.util.Log;

import org.json.JSONObject;

public abstract class BaseParser {

    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";

    public abstract Object parseObject(String json) throws Exception;

    public Object tryParse(String json) {

        Object parsedObject = null;
        try {
            JSONObject obj = new JSONObject(json);

            // Error check
            if (obj.getInt(STATUS_KEY) != 0) {
                String message = obj.getString(MESSAGE_KEY);
                if (TextUtils.isEmpty(message)) {
                    message = "No message";
                }
                return new RequestError(message);
            }

            parsedObject = parseObject(json);
        } catch (Exception ex) {
            Log.error("Parsing error!");
            ex.printStackTrace();
        }
        return parsedObject;
    }
}
