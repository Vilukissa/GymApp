package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.Log;

import java.util.List;

public class OperationHandle implements OnOperationCompleteListener {

    public enum OperationHandleConfig {
        DONT_CACHE,
        USE_INTERNAL_LISTENER
    }

    private final String mOperationId;
    private OperationModel mOperationModel;

    private Object mResult;
    private boolean mUseCache = true;
    private boolean mUsePersistentCache;

    private OnOperationCompleteListener mExternalListener;
    private OnOperationCompleteListener mInternalListener;
    private boolean mIsActive;

    public OperationHandle(String operationId, OperationModel operationModel) {
        mOperationId = operationId;
        mOperationModel = operationModel;
    }

    public OperationHandle(String operationId, OperationModel operationModel, List<OperationHandleConfig> configs) {
        this(operationId, operationModel);
        mUseCache = !configs.contains(OperationHandleConfig.DONT_CACHE);
        if (configs.contains(OperationHandleConfig.USE_INTERNAL_LISTENER)) {
            mInternalListener = (OnOperationCompleteListener) operationModel;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (mUseCache || mUsePersistentCache) {
            Log.debug("Caching data");
            mResult = data;
        }
        if (mInternalListener != null) {
            mInternalListener.onSuccess(data);
        }
        returnSuccessResult(data);
    }

    @Override
    public void onFailure(RequestError error) {
        if (mUsePersistentCache) {
            Log.debug("Caching error");
            mResult = error;
        }
        if (mInternalListener != null) {
            mInternalListener.onFailure(error);
        }
        returnErrorResult(error);
    }

    private void returnSuccessResult(Object result) {
        if (mIsActive) {
            Log.debug("Returning success result to fragment");
            mExternalListener.onSuccess(result);
        }
        if (!mUseCache && !mUsePersistentCache) {
            mOperationModel.removeHandle(mOperationId);
        }
    }

    private void returnErrorResult(RequestError result) {
        if (mIsActive) {
            Log.debug("Returning error result to fragment");
            mExternalListener.onFailure(result);
            mOperationModel.removeHandle(mOperationId);
        }
    }

    protected void setListener(OnOperationCompleteListener listener, boolean isPersistent) {
        mIsActive = true;
        mUsePersistentCache = isPersistent;
        mExternalListener = listener;

        notifyListenerIfResultCached();
    }

    private void notifyListenerIfResultCached() {
        if (mResult instanceof RequestError) {
            Log.debug("Error was cached");
            returnErrorResult((RequestError) mResult);
            mOperationModel.removeHandle(mOperationId);

        } else if (mResult != null) {
            Log.debug("Result was cached");
            returnSuccessResult(mResult);
        }
    }

    public void updateListener(OnOperationCompleteListener listener) {
        mExternalListener = listener;
    }

    public void notifyOnResume() {
        mIsActive = true;
        if (mUsePersistentCache) {
            notifyListenerIfResultCached();
        }
    }

    public void notifyOnPause() {
        mIsActive = false;
    }

    public Class<?> getOperationModelClass() {
        return mOperationModel.getClass();
    }

    public String getOperationId() {
        return mOperationId;
    }

    public OnOperationCompleteListener getExternalListener() {
        return mExternalListener;
    }

    public boolean isResultError() {
        return mResult instanceof RequestError;
    }
}
