package com.calicode.gymapp.app.view.workout;

import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.Component;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutTaskModel implements Component {

    private List<WorkoutMove> mWorkoutMoveList = new ArrayList<WorkoutMove>();

    public void setWorkoutMoveList(List<WorkoutMove> workoutMoveList) {
        mWorkoutMoveList = workoutMoveList;
    }

    public List<WorkoutMove> getWorkoutMoveList() {
        return mWorkoutMoveList;
    }
}
