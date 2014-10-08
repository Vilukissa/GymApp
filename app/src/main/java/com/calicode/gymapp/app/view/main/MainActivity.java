package com.calicode.gymapp.app.view.main;

import com.calicode.gymapp.app.navigation.Navigator;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ComponentProvider.get().getComponent(Navigator.class).resetNavigator();
            ComponentProvider.get().destroyComponents();
        }
    }
}
