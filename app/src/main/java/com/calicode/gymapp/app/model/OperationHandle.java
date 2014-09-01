package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.Log;

import java.util.List;

public class OperationHandle implements OnOperationCompleteListener {

    public enum OperationHandleConfig {
        DONT_CACHE;
    }

    private final String mOperationId;
    private final OperationModel mOperationModel;

    private Object mResult;
    private boolean mUseCache = true;

    private OnOperationCompleteListener mExternalListener;
    private boolean mIsActive;

    public OperationHandle(String operationId, OperationModel operationModel) {
        mOperationId = operationId;
        mOperationModel = operationModel;
    }

    public OperationHandle(String operationId, OperationModel operationModel, List<OperationHandleConfig> configs) {
        this(operationId, operationModel);
        mUseCache = !configs.contains(OperationHandleConfig.DONT_CACHE);
    }

    @Override
    public void onSuccess(Object data) {
        if (mUseCache) {
            Log.debug("Caching data");
            mResult = data;
        }
        returnSuccessResult(data, mExternalListener);
    }

    @Override
    public void onFailure(RequestError error) {
        returnErrorResult(error, mExternalListener);
    }

    private void returnSuccessResult(Object result, OnOperationCompleteListener listener) {
        if (mIsActive) {
            Log.debug("Returning success result to fragment");
            listener.onSuccess(result);
        }
        if (!mUseCache) {
            mOperationModel.removeHandle(mOperationId);
        }
    }

    private void returnErrorResult(RequestError result, OnOperationCompleteListener listener) {
        if (mIsActive) {
            Log.debug("Returning error result to fragment");
            listener.onFailure(result);
        }
        mOperationModel.removeHandle(mOperationId);
    }

    protected void setListener(OnOperationCompleteListener listener) {
        mIsActive = true;

        if (mResult instanceof RequestError) {
            Log.debug("Cached error");
            returnErrorResult((RequestError) mResult, listener);
        } else if (mResult != null) {
            Log.debug("Cached result");
            returnSuccessResult(mResult, listener);
        } else {
            mResult = null;
            mExternalListener = listener;
        }
    }

    public boolean checkCacheForResult() {
        if (mResult instanceof RequestError) {
            returnErrorResult((RequestError) mResult, mExternalListener);
            return true;
        } else if (mResult != null) {
            returnSuccessResult(mResult, mExternalListener);
            return true;
        } else {
            return false;
        }
    }

    public void notifyOnResume() {
        mIsActive = true;
    }

    public void notifyOnPause() {
        mIsActive = false;
    }
}
