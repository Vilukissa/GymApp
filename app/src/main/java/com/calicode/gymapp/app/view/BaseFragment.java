package com.calicode.gymapp.app.view;

import android.support.v4.app.Fragment;

import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandleHelper;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public abstract class BaseFragment extends Fragment {

    private OperationHandleHelper mOperationHandleHelper
            = ComponentProvider.get().getComponent(OperationHandleHelper.class);

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

    public void attachHandle(OperationHandle handle) {
        mOperationHandleHelper.attach(handle);
    }
}
