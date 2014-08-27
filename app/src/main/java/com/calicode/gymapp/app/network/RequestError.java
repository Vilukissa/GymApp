package com.calicode.gymapp.app.network;

import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.calicode.gymapp.app.GymApp;
import com.calicode.gymapp.app.R;

public class RequestError extends Exception {

    public RequestError(VolleyError volleyError) {
        super(volleyError.getMessage(), volleyError.getCause());
    }

    public String getErrorMessage() {
        if (! TextUtils.isEmpty(getMessage())) {
            return getMessage();
        } else if (! TextUtils.isEmpty(getLocalizedMessage())) {
            return getLocalizedMessage();
        } else {
            return GymApp.sAppContext.getString(R.string.common_error);
        }
    }
}
