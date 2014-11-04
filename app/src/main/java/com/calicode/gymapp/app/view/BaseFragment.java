package com.calicode.gymapp.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandleHelper;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.navigation.Navigator;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public abstract class BaseFragment extends Fragment {

    private final OperationHandleHelper mOperationHandleHelper = new OperationHandleHelper();
    private Navigator mNavigator = ComponentProvider.get().getComponent(Navigator.class);
    private View mBaseView;

    protected int getLayoutResource() {
        return -1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mOperationHandleHelper.onCreate(savedInstanceState, this);
        } catch (Exception ex) {
            Log.error("Could not use " + mOperationHandleHelper.getClass().getSimpleName()
                    + "'s onCreate method", ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResource = getLayoutResource();
        if (layoutResource != -1) {
            mBaseView = inflater.inflate(layoutResource, container, false);
            return mBaseView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
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

    protected View getBaseView() {
        return mBaseView;
    }

    protected void setTextViewData(int textViewId, String text) {
        ((TextView) mBaseView.findViewById(textViewId)).setText(text);
    }

    /** Listener lives through the Fragment's onCreate-onDestroy cycles */
    public void attachPersistentListener(OperationHandle handle, JsonOperation.OnOperationCompleteListener listener) {
        mOperationHandleHelper.attachPersistentListener(handle, listener);
    }

    /** Listener lives through the Fragment's onResume-onPause cycles */
    public void attachListener(OperationHandle handle, JsonOperation.OnOperationCompleteListener listener) {
        mOperationHandleHelper.attachWeakListener(handle, listener);
    }

    protected void navigateToLocation(NavigationLocation location) {
        mNavigator.navigateToLocation(location);
    }

    protected void navigateBack() {
        mNavigator.navigateBack();
    }
}