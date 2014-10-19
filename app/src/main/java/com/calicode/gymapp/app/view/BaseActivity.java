package com.calicode.gymapp.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.UserSessionManager;
import com.calicode.gymapp.app.model.UserSessionManager.LoginState;
import com.calicode.gymapp.app.model.UserSessionManager.OnLoginStateChangedListener;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.navigation.Navigator;
import com.calicode.gymapp.app.navigation.Navigator.OnNavigateListener;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public class BaseActivity extends FragmentActivity implements OnNavigateListener, OnLoginStateChangedListener {

    private Navigator mNavigator = ComponentProvider.get().getComponent(Navigator.class);
    private UserSessionManager mUserSessionManager = ComponentProvider.get().getComponent(UserSessionManager.class);
    private LoginState mLoginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_view);
        mNavigator.addListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginState = mUserSessionManager.getLoginState();
        mUserSessionManager.addOnLoginStateChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUserSessionManager.removeOnLoginStateChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNavigator.removeListener(this);
    }

    @Override
    public void onBackPressed() {
        if (!mNavigator.onBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    public void navigate(NavigationLocation location) {
        replaceFragment(location.getFragmentClass());
    }

    private void replaceFragment(Class<? extends Fragment> fragmentClass) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFragment == null || !currentFragment.getClass().equals(fragmentClass)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, Fragment.instantiate(this, fragmentClass.getName()))
                    .commit();
        }
    }

    @Override
    public void loginStateChanged(LoginState loginState) {
        if (mLoginState == loginState) {
            return;
        }
        Log.debug("Login state changed: " + loginState);

        mLoginState = loginState;
        if (loginState == LoginState.LOGGED_OUT) {
            mNavigator.logout();
            mUserSessionManager.addOnLoginStateChangedListener(this);
        }
    }
}
