package com.calicode.gymapp.app.model.logout;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public class LogoutModel extends OperationModel implements OnOperationCompleteListener {

    public LogoutModel() {
        super(OperationHandleConfig.DONT_CACHE,
                OperationHandleConfig.USE_INTERNAL_LISTENER);
    }

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getLogoutOperation();
    }

    public void logout() {
        executeOperation();
    }

    @Override
    public void onSuccess(Object data) {
        ComponentProvider.get().getComponent(UserSessionManager.class).loggedOut();
    }

    @Override
    public void onFailure(RequestError error) {
    }
}
