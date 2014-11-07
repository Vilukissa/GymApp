package com.calicode.gymapp.app.model.workout.movename;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.network.JsonOperation;

public class MoveNameModel extends OperationModel {

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getMoveNameOperation();
    }

    public OperationHandle fetchMoveNames() {
        return executeOperation();
    }
}
