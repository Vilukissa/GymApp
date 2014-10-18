package com.calicode.gymapp.app.model.workout.days;

import com.calicode.gymapp.app.model.workout.WorkoutDay;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDaysData {

    @JsonProperty("workoutList")
    private List<WorkoutDay> mWorkoutList = new ArrayList<WorkoutDay>();

    public List<WorkoutDay> getWorkoutList() {
        return mWorkoutList;
    }
}
