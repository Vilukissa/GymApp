package com.calicode.gymapp.app.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OperationHandleHelper {

    private static final String OPERATION_MODEL_CLASS = "opertaionModelClass";
    private static final String OPERATION_HANDLE_ID = "operationHandleId";
    private static final String PERSISTENT_LISTENER_COUNT = "persistListenerCount";
    private static final String OPERATION_LISTENER_CLASS = "operationListenerClass";

    private Map<String, OperationHandle> mPersistentListeners = new HashMap<String, OperationHandle>();
    private List<OperationHandle> mListeners = new ArrayList<OperationHandle>();

    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState, Fragment createdFragment) throws Exception {

        if (savedInstanceState != null
                && savedInstanceState.containsKey(PERSISTENT_LISTENER_COUNT)) {

            final int listenerCount = savedInstanceState.getInt(PERSISTENT_LISTENER_COUNT);
            for (int i = 0; i < listenerCount; ++i) {

                Class operationModelClass = (Class) savedInstanceState.getSerializable(OPERATION_MODEL_CLASS + i);
                String operationHandleId = savedInstanceState.getString(OPERATION_HANDLE_ID + i);
                Class operationListenerClass = (Class) savedInstanceState.getSerializable(OPERATION_LISTENER_CLASS + i);

                OperationModel model = (OperationModel) ComponentProvider.get().getComponent(operationModelClass);
                OperationHandle handle = model.getOperationHandle(operationHandleId);

                Constructor<?> listenerConstructor = operationListenerClass.getDeclaredConstructor(createdFragment.getClass());
                if (!listenerConstructor.isAccessible()) {
                    listenerConstructor.setAccessible(true);
                }
                OnOperationCompleteListener listener =
                        (OnOperationCompleteListener) listenerConstructor.newInstance(createdFragment);
                handle.updateListener(listener);

                mPersistentListeners.put(handle.getClass().getName() + handle.getOperationId(), handle);
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        int i = 0;
        for (Entry<String, OperationHandle> entry : mPersistentListeners.entrySet()) {
            OperationHandle handle = entry.getValue();
            Class operationModelClass = handle.getOperationModelClass();
            String operationHandleId = handle.getOperationId();

            outState.putSerializable(OPERATION_MODEL_CLASS + i, operationModelClass);
            outState.putString(OPERATION_HANDLE_ID + i, operationHandleId);
            outState.putSerializable(OPERATION_LISTENER_CLASS + i, handle.getExternalListener().getClass());
            ++i;
        }
        outState.putInt(PERSISTENT_LISTENER_COUNT, i);
    }

    public void onFragmentResume() {
        Iterator<OperationHandle> iterator = mListeners.iterator();
        while (iterator.hasNext()) {
            OperationHandle handle = iterator.next();
            handle.notifyOnResume();
            iterator.remove();
        }

        for (Entry<String, OperationHandle> entry : mPersistentListeners.entrySet()) {
            entry.getValue().notifyOnResume();
        }
    }

    public void onFragmentPause() {
        for (OperationHandle handle : mListeners) {
            handle.notifyOnPause();
        }
        Iterator<Entry<String, OperationHandle>> iterator = mPersistentListeners.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, OperationHandle> entry = iterator.next();
            entry.getValue().notifyOnPause();
            if (entry.getValue().isResultError()) {
                iterator.remove();
            }
        }
    }

    public void attachPersistentListener(OperationHandle handle, OnOperationCompleteListener listener) {
        // Holds listeners until the fragment is destroyed for good
        handle.setListener(listener, true);
        mPersistentListeners.put(handle.getClass().getName() + handle.getOperationId(), handle);
    }

    public void attachWeakListener(OperationHandle handle, OnOperationCompleteListener listener) {
        // Holds listeners only onPause-onResume cycles
        handle.setListener(listener, false);
        mListeners.add(handle);
    }
}
