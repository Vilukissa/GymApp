package com.calicode.gymapp.app.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.authentication.AuthenticationData;
import com.calicode.gymapp.app.model.authentication.AuthenticationModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

public class MainFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });

        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(NavigationLocation.LOGIN);
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
        String state = getString(R.string.authenticated);
        String token = data.getAuthToken();
        ((TextView) getContentView().findViewById(R.id.authenticationStateText)).setText(state);
        ((TextView) getContentView().findViewById(R.id.authenticationTokenText)).setText(token);
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