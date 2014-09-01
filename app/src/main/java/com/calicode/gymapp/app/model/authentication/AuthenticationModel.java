package com.calicode.gymapp.app.model.authentication;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.network.JsonOperation;

public class AuthenticationModel extends OperationModel {

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getAuthenticationOperation();
    }

    public OperationHandle authenticate() {
        return executeOperation();
    }
}
