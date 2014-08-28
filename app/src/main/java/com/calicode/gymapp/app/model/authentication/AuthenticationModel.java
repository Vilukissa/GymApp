package com.calicode.gymapp.app.model.authentication;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class AuthenticationModel extends OperationModel {

    public AuthenticationModel() {
    }

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getAuthenticationOperation();
    }

    public OperationHandle authenticate() {
        return executeOperation();
    }
}
