package com.calicode.gymapp.app.model.authentication;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public class AuthenticationModel extends OperationModel implements OnOperationCompleteListener {

    public AuthenticationModel() {
        super(OperationHandleConfig.USE_INTERNAL_LISTENER);
    }

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getAuthenticationOperation();
    }

    public OperationHandle authenticate() {
        return executeOperation();
    }

    @Override
    public void onSuccess(Object data) {
        AuthenticationData authenticationData = (AuthenticationData) data;
        ComponentProvider.get().getComponent(UserSessionManager.class).setAuthToken(authenticationData.getAuthToken());
    }

    @Override
    public void onFailure(RequestError error) {
    }
}
