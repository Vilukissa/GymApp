package com.calicode.gymapp.app.model.workout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutDay {

    @JsonProperty("day")
    private Date mDay;

    @JsonProperty("workoutId")
    private String mWorkoutId;

    @JsonProperty("moveList")
    private List<WorkoutMove> mMoveList = new ArrayList<WorkoutMove>();

    public String getDay() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return df.format(mDay);
    }

    public String getWorkoutId() {
        return mWorkoutId;
    }

    public List<WorkoutMove> getMoveList() {
        return mMoveList;
    }
}
