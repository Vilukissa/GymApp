package com.calicode.gymapp.app.model;

import android.os.Bundle;

import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OperationHandleHelper {

    private static final String LISTENERS = "listeners";

    private List<OperationHandle> mListeners = new ArrayList<OperationHandle>();

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(LISTENERS)) {}
    }

    public void onFragmentResume() {
        Iterator<OperationHandle> iterator = mListeners.iterator();
        while (iterator.hasNext()) {
            OperationHandle handle = iterator.next();
            handle.notifyOnResume();
            // handle.checkCacheForResult();
            iterator.remove();
        }
    }

    public void onFragmentPause() {
        for (OperationHandle handle : mListeners) {
            handle.notifyOnPause();
        }
    }

    public void onSaveInstanceState(Bundle outState) {}

    public void attachPersistentListener(OperationHandle handle, OnOperationCompleteListener listener) {}

    public void attachWeakListener(OperationHandle handle, OnOperationCompleteListener listener) {
        // Holds listeners only onPause-onResume cycles
        handle.setListener(listener);
        mListeners.add(handle);
    }
}
