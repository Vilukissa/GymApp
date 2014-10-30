package com.calicode.gymapp.app.network;

import com.android.volley.VolleyError;
import com.calicode.gymapp.app.util.Log;
import com.fasterxml.jackson.databind.ObjectReader;

import org.json.JSONObject;

import static com.calicode.gymapp.app.util.Jackson.OBJECT_READER;

public class JsonParser {

    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";

    private Class<?> mDataClass;

    protected JsonParser() {}

    public JsonParser(Class<?> dataClass) {
        mDataClass = dataClass;
    }

    public Object parseObject(ObjectReader objectReader, String json, Class<?> dataClass) throws Exception {
        return objectReader.withType(dataClass).readValue(json);
    }

    public final Object tryParse(String json) throws VolleyError {
        try {
            JSONObject obj = new JSONObject(json);

            // Error check
            if (obj.getInt(STATUS_KEY) != 0) {
                String message = obj.getString(MESSAGE_KEY);
                throw new VolleyError(message);
            }

            // Parse result
            return parseObject(OBJECT_READER, json, mDataClass);
        } catch (VolleyError error) {
            throw error;

        } catch (Exception ex) {
            Log.error("Parsing error", ex);
        }
        return null;
    }
}
