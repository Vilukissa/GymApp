package com.calicode.gymapp.app.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.calicode.gymapp.app.GymApp;
import com.calicode.gymapp.app.util.Log;

public class VolleyHandler {

    private static VolleyHandler sVolleyHandler;
    private RequestQueue mRequestQueue;

    public static synchronized VolleyHandler get() {
        if (sVolleyHandler == null) {
            sVolleyHandler = new VolleyHandler();
        }
        return sVolleyHandler;
    }

    private synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(GymApp.sAppContext);
        }
        return mRequestQueue;
    }

    @SuppressWarnings("unused")
    public void clearCache(String url) {
        Log.debug("Clearing cache by url: " + url);
        getRequestQueue().getCache().remove(url);
    }

    public void addToRequestQueue(JsonOperation req) {
        Request request = req.getRequest();
        getRequestQueue().add(request);
    }
}
