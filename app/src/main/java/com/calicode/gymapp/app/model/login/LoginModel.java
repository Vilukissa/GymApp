package com.calicode.gymapp.app.model.login;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public class LoginModel extends OperationModel implements OnOperationCompleteListener {

    private String mUserName;
    private String mPassword;

    public LoginModel() {
        super(OperationHandleConfig.DONT_CACHE,
                OperationHandleConfig.USE_INTERNAL_LISTENER);
    }

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getLoginOperation(mUserName, mPassword);
    }

    public OperationHandle login(String username, String password) {
        mUserName = username;
        mPassword = password;
        return executeOperation();
    }

    @Override
    public void destroy() {
        super.destroy();
        mUserName = null;
        mPassword = null;
    }

    @Override
    public void onSuccess(Object data) {
        LoginData loginData = (LoginData) data;
        ComponentProvider.get().getComponent(UserSessionManager.class).setLoginData(loginData);
    }

    @Override
    public void onFailure(RequestError error) {
    }
}