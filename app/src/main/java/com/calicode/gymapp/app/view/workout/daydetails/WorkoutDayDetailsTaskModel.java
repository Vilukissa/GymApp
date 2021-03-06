package com.calicode.gymapp.app.view.workout.daydetails;

import com.calicode.gymapp.app.model.workout.WorkoutDay;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class WorkoutDayDetailsTaskModel implements SessionComponent {

    private WorkoutDay mSelectedWorkoutDay;

    public void setSelectedWorkoutDay(WorkoutDay workoutDay) {
        mSelectedWorkoutDay = workoutDay;
    }

    public WorkoutDay getSelectedWorkoutDay() {
        return mSelectedWorkoutDay;
    }

    @Override
    public void destroy() {
        mSelectedWorkoutDay = null;
    }
}
