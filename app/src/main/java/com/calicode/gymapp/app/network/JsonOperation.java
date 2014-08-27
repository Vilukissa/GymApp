package com.calicode.gymapp.app.network;

import com.android.volley.DefaultRetryPolicy;
import com.calicode.gymapp.app.Config;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class JsonOperation {

    public interface OnOperationCompleteListener {
        public void onSuccess(Object data);
        public void onFailure(RequestError error);
    }

    private static final int SOCKET_TIMEOUT_MS = 3000;

    private JsonRequest mRequest;

    public abstract String getUrl();

    public Map<String, String> getParams() {
        return new HashMap<String, String>();
    }

    public JsonOperation(int method, JsonParser parser) {
        mRequest = new JsonRequest(method, createUrl(), createJsonBody(), parser);
        mRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void setOperationListener(OnOperationCompleteListener listener) {
        mRequest.setListener(listener);
    }

    public void execute() {
        ComponentProvider.get().getComponent(VolleyHandler.class)
                .addToRequestQueue(mRequest);
    }

    private String createUrl() {
        return new StringBuilder()
                .append(Config.SERVER_ADDRESS)
                .append(getUrl()).toString();
    }

    private String createJsonBody() {
        JSONObject obj = new JSONObject(getParams());
        return obj.toString();
    }
}