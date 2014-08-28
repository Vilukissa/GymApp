package com.calicode.gymapp.app.model.login;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class LoginModel extends OperationModel {

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getLoginOperation();
    }
}
