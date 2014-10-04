package com.calicode.gymapp.app.model.workout;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.network.JsonOperation;

public class WorkoutDaysModel extends OperationModel {

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getWorkoutDaysFetchOperation();
    }

    public OperationHandle fetchWorkoutDays() {
        return executeOperation();
    }
}
