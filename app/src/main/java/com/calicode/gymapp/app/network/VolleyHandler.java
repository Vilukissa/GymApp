package com.calicode.gymapp.app.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.calicode.gymapp.app.GymApp;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class VolleyHandler implements SessionComponent {

    private static final String DEFAULT_TAG = "default_request_tag";

    private RequestQueue mRequestQueue;

    private synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(GymApp.sAppContext);
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request request) {
        Log.debug("Adding request to URL: " + request.getUrl());
        request.setTag(DEFAULT_TAG);
        getRequestQueue().add(request);
    }

    private void cancelRequests() {
        Log.debug("Canceling all requests with tag: " + DEFAULT_TAG);
        getRequestQueue().cancelAll(DEFAULT_TAG);
    }

    @Override
    public void destroy() {
        cancelRequests();
    }
}