package com.calicode.gymapp.app.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.authentication.AuthenticationData;
import com.calicode.gymapp.app.model.authentication.AuthenticationOperation;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public class MainFragment extends Fragment {

    private View mAuthButton;
    private View mContentLayout;
    private View mProgressLayout;
    private View mErrorLayout;

    private View.OnClickListener mAuthButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showProgress();
            testLogin();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mAuthButton = rootView.findViewById(R.id.authButton);
        mContentLayout = rootView.findViewById(R.id.contentLayout);
        mProgressLayout = rootView.findViewById(R.id.progressLayout);
        mErrorLayout = rootView.findViewById(R.id.errorLayout);

        mAuthButton.setOnClickListener(mAuthButtonListener);
        mErrorLayout.setOnClickListener(mAuthButtonListener);

        return rootView;
    }

    private void showContent() {
        mProgressLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    private void showError(String error) {
        ((TextView) mErrorLayout.findViewById(R.id.errorText)).setText(error);
        mProgressLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        mErrorLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
        mProgressLayout.setVisibility(View.VISIBLE);
    }

    private void testLogin() {
        AuthenticationOperation operation = ComponentProvider.get()
                .getComponent(OperationCreator.class).getAuthenticationOperation();
        operation.setOperationListener(new JsonOperation.OnOperationCompleteListener() {

            @Override
            public void onSuccess(Object data) {
                ((TextView) mContentLayout.findViewById(R.id.authenticationStateText))
                        .setText(getString(R.string.authenticated));
                ((TextView) mContentLayout.findViewById(R.id.authenticationTokenText))
                        .setText(((AuthenticationData) data).getAuthToken());
                mAuthButton.setVisibility(View.GONE);
                showContent();
            }

            @Override
            public void onFailure(RequestError error) {
                showError(error.getErrorMessage());
            }
        });

        operation.execute();
    }
}