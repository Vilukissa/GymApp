package com.calicode.gymapp.app.model.workout.add;

import com.calicode.gymapp.app.model.OperationCreator;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.OperationHandle.OperationHandleConfig;
import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.network.JsonOperation;

import java.util.List;

public class AddWorkoutDayModel extends OperationModel {

    private String mDay;
    private List<WorkoutMove> mMoves;

    public AddWorkoutDayModel() {
        super(OperationHandleConfig.DONT_CACHE);
    }

    public OperationHandle addWorkout(String day, List<WorkoutMove> moves) {
        mDay = day;
        mMoves = moves;
        return executeOperation();
    }

    @Override
    public JsonOperation getOperation(OperationCreator operationCreator) {
        return operationCreator.getAddWorkoutDayOperation(mDay, mMoves);
    }
}
