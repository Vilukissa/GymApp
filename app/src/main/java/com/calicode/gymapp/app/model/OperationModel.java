package com.calicode.gymapp.app.model;

import android.util.LruCache;

import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class OperationModel implements SessionComponent {

    private static final String DEFAULT_ID = "default_id";

    public abstract JsonOperation getOperation(OperationCreator operationCreator);

    /**
     * Holds the instances to operation handles
     * Key = operation ID
     * Value = operation handle
     */
    private LruCache<String, OperationHandle> mHandleCache;
    private List<OperationHandleConfig> mConfigs = new ArrayList<OperationHandleConfig>();

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

    @SuppressWarnings("unchecked")
    public OperationModel(OperationHandleConfig... configs) {
        this();
        mConfigs = new ArrayList(Arrays.asList(configs));
    }

    protected OperationHandle executeOperation() {
        return executeOperation(DEFAULT_ID);
    }

    protected OperationHandle executeOperation(String operationId) {
        // Cases:
        // 1) Not found in handle cache                                                             => execute operation
        // 2) Found in handle cache AND request not pending AND has result (=caches)                => give existing handle back
        // 3) Found in handle cache AND request is pending                                          => give existing handle back
        // 4) If error is received                                                                  => handle removes itself from cache so next time 'handle == null'
        // 5) If handle doesn't cache result                                                        => handle removes itself from cache so next time 'handle == null'

        JsonOperation operation = getOperation(ComponentProvider.get()
                .getComponent(OperationCreator.class));

        OperationHandle handle = mHandleCache.get(operationId);

        if (handle == null) {
            // Cases 1 & 4 & 5
            Log.debug("Creating new operation handle and executing");
            handle = new OperationHandle(operationId, this, mConfigs);
            mHandleCache.put(operationId, handle);
            operation.execute(handle);
        } else {
            // Cases 2 & 3
            Log.debug("Attaching to existing operation handle");
        }

        return handle;
    }

    protected void removeHandle(String operationId) {
        Log.debug("Removing operation handle from operation handle cache");
        mHandleCache.remove(operationId);
    }

    public OperationHandle getOperationHandle(String operationId) {
        return mHandleCache.get(operationId);
    }

    public void clearCache() {
        Log.debug("Clearing cache");
        mHandleCache.evictAll();
    }

    @Override
    public void destroy() {
        clearCache();
    }
}