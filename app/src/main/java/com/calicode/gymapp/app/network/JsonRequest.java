package com.calicode.gymapp.app.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonRequest extends com.android.volley.toolbox.JsonRequest {

    private JsonParser mParser;
    private OnOperationCompleteListener mListener;

    @SuppressWarnings("unchecked")
    public JsonRequest(int method, String url, String requestBody, JsonParser parser) {
        super(method, url, requestBody, null, null);
        mParser = parser;
    }

    public void setListener(OnOperationCompleteListener listener) {
        mListener = listener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Log.debug("Raw response from server: " + json);
            Log.debug("Trying to parse response from " + getUrl() + " => " + new JSONObject(json));
            Object dataObject = mParser.tryParse(json);

            return Response.success(dataObject, HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            return Response.error((VolleyError) e);
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        mListener.onSuccess(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        Log.debug("Error from " + getUrl() + " => " + error);
        mListener.onFailure(new RequestError(error));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}