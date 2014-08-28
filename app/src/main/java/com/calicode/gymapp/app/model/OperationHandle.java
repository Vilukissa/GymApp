package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.model.OperationHandleHelper.FragmentState;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.Log;

import java.util.Arrays;
import java.util.List;

public class OperationHandle implements OnOperationCompleteListener {

    public enum OperationHandleConfig {
        DONT_CACHE,
        DONT_CACHE_ERROR;
    }

    /** When attaching new operation handle, assume that we have visible fragment */
    private FragmentState mFragmentState = FragmentState.VISIBLE;
    private Object mResult;
    private String mOperationId;
    private OnOperationCompleteListener mListener;
    private boolean mUseCache = true;
    private boolean mUseErrorCache = true;
    private boolean mResultReturned;

    /** Default operation handle uses full result caching (caches also errors) */
    public OperationHandle(String operationId) {
        mOperationId = operationId;
    }

    public OperationHandle(String operationId, OperationHandleConfig... configs) {
        this(operationId, Arrays.asList(configs));
    }

    private OperationHandle(String operationId, List<OperationHandleConfig> configs) {
        this(operationId);
        mUseCache = !configs.contains(OperationHandleConfig.DONT_CACHE);
        mUseErrorCache = !configs.contains(OperationHandleConfig.DONT_CACHE_ERROR);
    }

    @Override
    public void onSuccess(Object data) {
        if (mUseCache) {
            mResult = data;
        }
        if (mListener != null && mFragmentState == FragmentState.VISIBLE) {
            returnSuccessResult(data, mListener);
        }
    }

    @Override
    public void onFailure(RequestError error) {
        if (mUseCache && mUseErrorCache) {
            mResult = error;
        }
        if (mListener != null && mFragmentState == FragmentState.VISIBLE) {
            returnErrorResult(error, mListener);
        }
    }

    private void returnSuccessResult(Object result, OnOperationCompleteListener listener) {
        mResultReturned = true;
        listener.onSuccess(result);
    }

    private void returnErrorResult(RequestError result, OnOperationCompleteListener listener) {
        mResultReturned = true;
        listener.onFailure(result);
    }

    public void setListener(OnOperationCompleteListener listener) {
        if (mResult instanceof RequestError) {
            Log.debug("Returning cached error");
            returnErrorResult((RequestError) mResult, listener);
        } else if (mResult != null) {
            Log.debug("Returning cached result");
            returnSuccessResult(mResult, listener);
        } else {
            mResultReturned = false;
            mListener = listener;
        }
    }

    public boolean isResultReturned() {
        return mResultReturned;
    }

    public void notifyFragmentStateChange(OperationHandleHelper.FragmentState state) {
        mFragmentState = state;
    }

    public String getOperationId() {
        return mOperationId;
    }
}
