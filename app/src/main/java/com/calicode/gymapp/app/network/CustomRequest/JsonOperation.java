package com.calicode.gymapp.app.network.customrequest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.calicode.gymapp.app.Config;
import com.calicode.gymapp.app.network.BaseParser;
import com.calicode.gymapp.app.network.VolleyHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class JsonOperation {

    public interface OnOperationCompleteListener {
        public void onSuccess(Object data);
        public void onFailure(RequestError error);
    }

    public static final int SOCKET_TIMEOUT_MS = 3000;

    private CustomRequest mRequest;

    public abstract String getUrl();

    public Map<String, String> getParams() {
        return new HashMap<String, String>();
    }

    public JsonOperation(int method, BaseParser parser) {
        mRequest = new CustomRequest(method, createUrl(), createJsonBody(), parser);
        mRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void setOperationListener(OnOperationCompleteListener listener) {
        mRequest.setListener(listener);
    }

    public void execute() {
        VolleyHandler.get().addToRequestQueue(mRequest);
    }

    private String createUrl() {
        return new StringBuilder()
                .append(Config.SERVER_ADRESS)
                .append(getUrl()).toString();
    }

    private String createJsonBody() {
        JSONObject obj = new JSONObject(getParams());
        return obj.toString();
    }
}