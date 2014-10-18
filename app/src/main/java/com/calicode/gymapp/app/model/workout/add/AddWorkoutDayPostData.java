package com.calicode.gymapp.app.model.workout.add;

import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutDayPostData {

    @JsonProperty("day")
    private String mDay;

    @JsonProperty("moves")
    private List<WorkoutMove> mMoves = new ArrayList<WorkoutMove>();

    public AddWorkoutDayPostData(String day, List<WorkoutMove> moves) {
        mDay = day;
        mMoves = moves;
    }
}
