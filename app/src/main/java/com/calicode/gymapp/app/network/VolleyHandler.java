package com.calicode.gymapp.app.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.calicode.gymapp.app.Config;
import com.calicode.gymapp.app.GymApp;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyHandler implements SessionComponent {

    private static final String DEFAULT_TAG = "default_request_tag";
    private static final List<String> URLS_WITHOUT_COOKIES = Arrays.asList(
            Config.SERVER_ADDRESS + "/v1/auth",
            Config.SERVER_ADDRESS + "/v1/login");

    private RequestQueue mRequestQueue;
    private CookieManager mCookieManager;

    private synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Cookie name for Session ID = sessionId
            mCookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(mCookieManager);
            mRequestQueue = Volley.newRequestQueue(GymApp.sAppContext);
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request request) {
        Log.debug("Adding request to URL: " + request.getUrl());

        request.setTag(DEFAULT_TAG);
        setCookieIfNeeded(request.getUrl());
        getRequestQueue().add(request);
    }

    private void cancelRequests() {
        Log.debug("Canceling all requests with tag: " + DEFAULT_TAG);
        getRequestQueue().cancelAll(DEFAULT_TAG);
    }

    private void setCookieIfNeeded(String url) {
        if (!URLS_WITHOUT_COOKIES.contains(url)) {
            Map<String, List<String>> cookies = new HashMap<String, List<String>>();
            List<String> cookie = Arrays.asList("sessionId=" + ComponentProvider.get()
                    .getComponent(UserSessionManager.class).getTokenForCookies());
            cookies.put("Set-Cookie", cookie);
            try {
                mCookieManager.put(new URI(url), cookies);
            } catch (Exception e) {
                Log.error("Failed to store cookie", e);
            }
        }
    }

    @Override
    public void destroy() {
        cancelRequests();
        mRequestQueue = null;
    }
}