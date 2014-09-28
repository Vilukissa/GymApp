package com.calicode.gymapp.app.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.navigation.Navigator;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;

public class BaseActivity extends FragmentActivity implements Navigator.OnNavigateListener {

    private Navigator mNavigator = ComponentProvider.get().getComponent(Navigator.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_view);
        mNavigator.addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNavigator.removeListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mNavigator.isStackEmpty()) {
            super.onBackPressed();
        } else {
            mNavigator.onBackPress();
        }
    }

    @Override
    public void onNavigate(NavigationLocation location) {
        replaceFragment(location.getFragmentClass());
    }

    private void replaceFragment(Class<? extends Fragment> fragmentClass) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (currentFragment == null || !currentFragment.equals(fragmentClass)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, Fragment.instantiate(this, fragmentClass.getName()))
                    .commit();
        }
    }
}
