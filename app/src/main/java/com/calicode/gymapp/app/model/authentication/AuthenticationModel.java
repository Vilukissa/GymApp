package com.calicode.gymapp.app.model.authentication;

import com.calicode.gymapp.app.model.OperationModel;
import com.calicode.gymapp.app.util.componentprovider.componentinterfaces.SessionComponent;

public class AuthenticationModel extends OperationModel implements SessionComponent {

    @Override
    public void destroy() {
    }
}
