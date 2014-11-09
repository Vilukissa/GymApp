package com.calicode.gymapp.app.model.workout.remove;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.network.JsonOperation;

public class RemoveWorkoutDayModel extends OperationModel {

    private int mWorkoutId;

    public RemoveWorkoutDayModel() {
        super(OperationHandleConfig.DONT_CACHE);
    }

    public OperationHandle removeWorkout(int workoutId) {
        mWorkoutId = workoutId;
        return executeOperation(String.valueOf(workoutId));
    }

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getRemoveWorkoutDayOperation(mWorkoutId);
    }
}
