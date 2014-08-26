package com.calicode.gymapp.app.network.customrequest;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.calicode.gymapp.app.network.BaseParser;
import com.calicode.gymapp.app.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends JsonRequest {

    private BaseParser mParser;
    private JsonOperation.OnOperationCompleteListener mListener;

    public CustomRequest(int method, String url, String requestBody, BaseParser parser) {
        super(method, url, requestBody, null, null);
        mParser = parser;
    }

    public void setListener(JsonOperation.OnOperationCompleteListener listener) {
        mListener = listener;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            Log.debug("Trying to parse response");

            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mParser.tryParse(json),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(Object response) {
        if (mListener != null) {
            Log.debug("Delivering response to listener");

            if (response instanceof RequestError) {
                mListener.onFailure((RequestError) response);
            } else {
                mListener.onSuccess(response);
            }
        } else {
            Log.error("Lost the operation listener");
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        // Callback for common errors and possible connection errors
        // see the implementation of CustomRequest.parseNetworkResponse!
        if (mListener != null) {
            Log.debug("Delivering error to listener");
            mListener.onFailure(new RequestError(error));
        } else {
            Log.error("Lost the operation listener");
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
