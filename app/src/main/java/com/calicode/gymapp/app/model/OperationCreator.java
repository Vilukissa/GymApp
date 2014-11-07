package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.model.authentication.AuthenticationOperation;
import com.calicode.gymapp.app.model.login.LoginOperation;
import com.calicode.gymapp.app.model.logout.LogoutOperation;
import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.model.workout.add.AddWorkoutDayOperation;
import com.calicode.gymapp.app.model.workout.days.WorkoutDaysOperation;
import com.calicode.gymapp.app.model.workout.movename.MoveNameOperation;
import com.calicode.gymapp.app.network.JsonOperation;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.Component;

import java.util.List;

public class OperationCreator implements Component {

    public AuthenticationOperation getAuthenticationOperation() {
        return new AuthenticationOperation();
    }

    public LoginOperation getLoginOperation(String userName, String password) {
        return new LoginOperation(userName, password);
    }

    public WorkoutDaysOperation getWorkoutDaysFetchOperation() {
        return new WorkoutDaysOperation();
    }

    public LogoutOperation getLogoutOperation() {
        return new LogoutOperation();
    }

    public JsonOperation getAddWorkoutDayOperation(String day, List<WorkoutMove> workoutMoves) {
        return new AddWorkoutDayOperation(day, workoutMoves);
    }

    public JsonOperation getMoveNameOperation() {
        return new MoveNameOperation();
    }
}
