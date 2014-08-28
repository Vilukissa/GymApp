package com.calicode.gymapp.app.model;

import android.util.LruCache;

import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public abstract class OperationModel implements SessionComponent {

    private static final String DEFAULT_ID = "default_id";

    public abstract JsonOperation getOperation(OperationCreator operationCreator);

    /**
     * Holds the instances to operation handles
     * Key = operation ID
     * Value = operation handle
     */
    private LruCache<String, OperationHandle> mHandleCache;
    private OperationHandleConfig[] mConfigs = new OperationHandleConfig[0];

    /**
     * Limits the operation handles count.
     * @return count of handles to hold
     */
    protected int cacheSize() {
        return 1;
    }

    public OperationModel() {
        mHandleCache = new LruCache<String, OperationHandle>(cacheSize());
    }

    public OperationModel(OperationHandleConfig... configs) {
        this();
        mConfigs = configs;
    }

    protected OperationHandle executeOperation() {
        return executeOperation(DEFAULT_ID);
    }

    protected OperationHandle executeOperation(String operationId) {
        // Cases:
        // 1) Not found in handle cache AND request not pending => execute operation
        // 2) Found in handle cache AND request not pending => give existing handle back
        // 3) Found in handle cache AND request is pending => give existing handle back
        // Impossible) Not found in handle cache AND Request is pending => log error

        JsonOperation operation = getOperation(ComponentProvider.get()
                .getComponent(OperationCreator.class));

        if (isCachedHandle(operationId)) {
            // Cases 2 & 3
            Log.debug("Attaching to existing operation handle");
            OperationHandle handle = mHandleCache.get(operationId);
            return handle;
        } else {
            if (operation.isRequestPending()) {
                // Impossible case
                Log.error("Operation is pending and operation handle did not found in cache!");
                return null;
            } else {
                // Case 1
                Log.debug("Creating new operation handle and executing");
                OperationHandle handle = new OperationHandle(operationId, mConfigs);
                operation.execute(handle);
                mHandleCache.put(operationId, handle);
                return handle;
            }
        }
    }

    public void clearCache() {
        mHandleCache.evictAll();
    }

    private boolean isCachedHandle(String operationId) {
        return mHandleCache.get(operationId) != null;
    }

    @Override
    public void destroy() {
        clearCache();
        mConfigs = new OperationHandleConfig[0];
    }
}
