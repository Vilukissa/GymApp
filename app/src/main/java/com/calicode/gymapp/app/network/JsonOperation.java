package com.calicode.gymapp.app.network;

import com.android.volley.DefaultRetryPolicy;
import com.calicode.gymapp.app.Config;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.util.Jackson;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

import java.util.HashMap;

public abstract class JsonOperation {

    public interface OnOperationCompleteListener {
        public void onSuccess(Object data);
        public void onFailure(RequestError error);
    }

    private static final int SOCKET_TIMEOUT_MS = 3000;

    private final int mMethod;
    private final JsonParser mParser;

    public abstract String getUrl();

    public Object getParams() {
        return new HashMap<String, String>();
    }

    public JsonOperation(int method, JsonParser parser) {
        mMethod = method;
        mParser = parser;
    }

    public void execute(OperationHandle operationHandle) {
        final String jsonBody = createJsonBody();
        Log.debug("JSON body: " + jsonBody);

        JsonRequest request = new JsonRequest(mMethod, createUrl(), jsonBody, mParser);
        request.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setListener(operationHandle);
        ComponentProvider.get().getComponent(VolleyHandler.class)
                .addToRequestQueue(request);
    }

    private String createUrl() {
        return Config.SERVER_ADDRESS + getUrl();
    }

    private String createJsonBody() {
        String json = "";
        try {
            json = Jackson.OBJECT_WRITER.writeValueAsString(getParams());
        } catch (Exception ex) {
            Log.error("Jackson failed to write values to object", ex);
        }
        return json;
    }
}