package com.calicode.gymapp.app.model.login;

import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class LoginModel extends OperationModel implements SessionComponent {

    @Override
    public void destroy() {
    }
}
