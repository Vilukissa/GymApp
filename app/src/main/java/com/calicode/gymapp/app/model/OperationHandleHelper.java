package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

import java.util.HashMap;
import java.util.Map;

public class OperationHandleHelper implements SessionComponent {

    public enum FragmentState {
        HIDDEN, VISIBLE;
    }

    private Map<String, OperationHandle> mHandles = new HashMap<String, OperationHandle>();

    public void onFragmentResume() {
        notifyFragmentStateChange(FragmentState.VISIBLE);
    }

    public void onFragmentPause() {
        notifyFragmentStateChange(FragmentState.HIDDEN);
    }

    public void attach(OperationHandle handle) {
        if (handle.isResultReturned()) {
            // We dont need the handle anymore
            mHandles.remove(handle.getOperationId());
        } else {
            // Replaces previous handle
            mHandles.put(handle.getOperationId(), handle);
        }
    }

    private void notifyFragmentStateChange(FragmentState state) {
        for (Map.Entry<String, OperationHandle> entry : mHandles.entrySet()) {
            entry.getValue().notifyFragmentStateChange(state);
        }
    }

    @Override
    public void destroy() {
        mHandles.clear();
    }
}
