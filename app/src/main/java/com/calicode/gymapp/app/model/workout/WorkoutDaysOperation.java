package com.calicode.gymapp.app.model.workout;

import com.android.volley.Request.Method;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonParser;

public class WorkoutDaysOperation extends JsonOperation {

    private static final int WORKOUT_COUNT = 5;

    public WorkoutDaysOperation() {
        super(Method.GET, new JsonParser(WorkoutDaysData.class));
    }

    @Override
    public String getUrl() {
        return "/v1/workout/count/" + WORKOUT_COUNT;
    }
}
