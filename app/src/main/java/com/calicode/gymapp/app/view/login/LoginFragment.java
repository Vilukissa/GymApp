package com.calicode.gymapp.app.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.login.LoginModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

public class LoginFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.login_form;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        final EditText usernameEditText = (EditText) view.findViewById(R.id.username);
        final EditText passwordEditText = (EditText) view.findViewById(R.id.password);
        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                // TODO: remove test code
                login("Vilpe", "1234");
            }
        });
        return view;
    }

    private void login(String username, String password) {
        showProgress();
        OperationHandle handle = ComponentProvider.get().getComponent(LoginModel.class)
                .login(username, password);
        OnOperationCompleteListener listener = new OnOperationCompleteListener() {
            @Override
            public void onSuccess(Object data) {
                navigate(NavigationLocation.MAIN_MENU);
            }

            @Override
            public void onFailure(RequestError error) {
                setErrorText(error.getErrorMessage());
                showError();
            }
        };

        attachListener(handle, listener);
    }
}