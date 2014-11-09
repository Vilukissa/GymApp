package com.calicode.gymapp.app.model.workout.remove;

import com.android.volley.Request.Method;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonParser;

public class RemoveWorkoutDayOperation extends JsonOperation {

    private final int mWorkoutId;

    public RemoveWorkoutDayOperation(int workoutId) {
        super(Method.DELETE, new JsonParser(Object.class));
        mWorkoutId = workoutId;
    }

    @Override
    public String getUrl() {
        return "/v1/workout/" + mWorkoutId;
    }
}
