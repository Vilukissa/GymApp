package com.calicode.gymapp.app.model.workout.add;

import com.android.volley.Request.Method;
import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.network.JsonParser;
import com.calicode.gymapp.app.util.Jackson;

import java.util.List;

public class AddWorkoutDayOperation extends JsonOperation {

    private final String mDay;
    private final List<WorkoutMove> mWorkoutMoves;

    public AddWorkoutDayOperation(String day, List<WorkoutMove> workoutMoves) {
        super(Method.POST, new JsonParser(Object.class));
        mDay = day;
        mWorkoutMoves = workoutMoves;
    }

    @Override
    public String getUrl() {
        return "/v1/workout/" + mDay;
    }

    @Override
    public Object getParams() {
        return new AddWorkoutDayPostData(mDay, mWorkoutMoves);
    }
}
