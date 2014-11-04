package com.calicode.gymapp.app.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.authentication.AuthenticationData;
import com.calicode.gymapp.app.model.authentication.AuthenticationModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.NetworkRequestFragment;

public class MainFragment extends NetworkRequestFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void errorOnClick() {
        authenticate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLocation(NavigationLocation.LOGIN);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        authenticate();
    }

    private void updateView(AuthenticationData data) {
        setTextViewData(R.id.authenticationStateText, getString(R.string.authenticated));
        setTextViewData(R.id.authenticationTokenText, data.getAuthToken());
    }

    private void authenticate() {
        showProgress();
        OperationHandle operation = ComponentProvider.get().getComponent(
                AuthenticationModel.class).authenticate();

        OnOperationCompleteListener listener =
                new OnOperationCompleteListener() {

            @Override
            public void onSuccess(Object data) {
                updateView((AuthenticationData) data);
                showContent();
            }

            @Override
            public void onFailure(RequestError error) {
                setErrorText(error.getErrorMessage());
                showError();
            }
        };

        attachListener(operation, listener);
    }
}