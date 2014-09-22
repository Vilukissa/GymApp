package com.calicode.gymapp.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandleHelper;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.navigation.Navigator;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public abstract class BaseFragment extends Fragment {

    private final OperationHandleHelper mOperationHandleHelper = new OperationHandleHelper();
    private Navigator mNavigator = ComponentProvider.get().getComponent(Navigator.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOperationHandleHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mOperationHandleHelper.onFragmentResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mOperationHandleHelper.onFragmentPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mOperationHandleHelper.onSaveInstanceState(outState);
    }

    /** Listener lives through the Fragment's onCreate-onDestroy cycles */
    public void attachPersistentListener(OperationHandle handle, JsonOperation.OnOperationCompleteListener listener) {
        mOperationHandleHelper.attachPersistentListener(handle, listener);
    }

    /** Listener lives through the Fragment's onResume-onPause cycles */
    public void attachListener(OperationHandle handle, JsonOperation.OnOperationCompleteListener listener) {
        mOperationHandleHelper.attachWeakListener(handle, listener);
    }

    protected void navigate(NavigationLocation location) {
        mNavigator.navigate(location);
    }

    protected void navigateBack() {
        mNavigator.navigateBack();
    }
}