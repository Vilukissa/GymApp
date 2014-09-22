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

    private View mContentLayout;
    private View mProgressLayout;
    private View mErrorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mContentLayout = rootView.findViewById(R.id.contentLayout);
        mProgressLayout = rootView.findViewById(R.id.progressLayout);
        mErrorLayout = rootView.findViewById(R.id.errorLayout);

        mErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });

        rootView.findViewById(R.id.clearCacheButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentProvider.get().getComponent(AuthenticationModel.class).clearCache();
                updateView(null);
            }
        });

        rootView.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate(NavigationLocation.LOGIN);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        authenticate();
    }

    private void showContent() {
        mProgressLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    private void showError(String error) {
        ((TextView) mErrorLayout.findViewById(R.id.errorText)).setText(
                getString(R.string.common_error_title) + ": " + error);
        mProgressLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        mErrorLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    private void updateView(AuthenticationData data) {
        String state = getString(data != null ? R.string.authenticated : R.string.not_authenticated);
        String token = data != null ? data.getAuthToken() : "";
        ((TextView) mContentLayout.findViewById(R.id.authenticationStateText)).setText(state);
        ((TextView) mContentLayout.findViewById(R.id.authenticationTokenText)).setText(token);
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
                showError(error.getErrorMessage());
            }
        };

        attachListener(operation, listener);
    }
}