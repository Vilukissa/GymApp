package com.calicode.gymapp.app.network.customrequest;

import android.text.TextUtils;

import com.android.volley.VolleyError;

public class RequestError extends Error {

    public RequestError(VolleyError volleyError) {
        super(volleyError.getMessage(), volleyError.getCause());
    }

    public RequestError(String message) {
        super(message);
    }

    public RequestError(String message, Throwable throwable) {
        super(message, throwable);
    }

    public String getErrorMessage() {
        if (TextUtils.isEmpty(getMessage())) {
            return getLocalizedMessage();
        } else {
            return getMessage();
        }
    }
}
