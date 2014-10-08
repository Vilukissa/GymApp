package com.calicode.gymapp.app.model;

import com.calicode.gymapp.app.model.authentication.AuthenticationOperation;
import com.calicode.gymapp.app.model.login.LoginOperation;
import com.calicode.gymapp.app.model.logout.LogoutOperation;
import com.calicode.gymapp.app.model.workout.WorkoutDaysOperation;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.Component;

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
}
