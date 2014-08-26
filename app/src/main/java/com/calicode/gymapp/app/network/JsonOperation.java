package com.calicode.gymapp.app.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.calicode.gymapp.app.util.Log;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class JsonOperation {

    public static final int SOCKET_TIMEOUT_MS = 5000;

    private OnOperationCompleteListener mOpCompleteListener;
    private CustomRequest mRequest;
    private BaseParser mParser;
    private String mUrl;

    public abstract String getUrl();

    public Map<String, String> getParams() {
        return new HashMap<String, String>();
    }

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // Callback for common errors and possible connection errors
            // see the implementation of CustomRequest.parseNetworkResponse!
            if (mOpCompleteListener != null) {
                mOpCompleteListener.onFailure(new RequestError(error.getMessage()));
            }
        }
    };

    public interface OnOperationCompleteListener {
        public void onSuccess(Object data);
        public void onFailure(RequestError error);
    }

    protected class CustomRequest extends JsonRequest {

        public CustomRequest(int method, String url, String body, Response.ErrorListener errorListener) {
            super(method, url, body, null, errorListener);
        }

        @Override
        protected Response parseNetworkResponse(NetworkResponse response) {
            try {
                Log.debug("Trying to parse response");
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(mParser.tryParse(json), HttpHeaderParser.parseCacheHeaders(response));

            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(Object response) {
            if (mOpCompleteListener != null) {
                Log.debug("Delivering response to listener");
                if (response instanceof RequestError) {
                    mOpCompleteListener.onFailure((RequestError) response);
                } else {
                    mOpCompleteListener.onSuccess(response);
                }
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

    public JsonOperation(int method, BaseParser parser) {
        mParser = parser;
        mUrl = createUrl();
        mRequest = new CustomRequest(method, mUrl, createJsonBody(), mErrorListener);
        mRequest.setRetryPolicy(new DefaultRetryPolicy(
                SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public Request getRequest() {
        return mRequest;
    }

    public void setOperationListener(OnOperationCompleteListener listener) {
        mOpCompleteListener = listener;
    }

    private String createUrl() {
        return new StringBuilder()
                .append("http://xxx/gym/REST")
                .append(getUrl()).toString();
    }

    private String createJsonBody() {
        JSONObject obj = new JSONObject(getParams());
        return obj.toString();
    }
}