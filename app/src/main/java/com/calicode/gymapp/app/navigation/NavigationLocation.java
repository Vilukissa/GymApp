package com.calicode.gymapp.app.navigation;

import android.support.v4.app.Fragment;

import com.calicode.gymapp.app.view.login.LoginFragment;
import com.calicode.gymapp.app.view.main.MainFragment;
import com.calicode.gymapp.app.view.workout.WorkoutDaysFragment;

public enum NavigationLocation {

    MAIN(MainFragment.class),
    LOGIN(LoginFragment.class),
    WORKOUT_DAYS(WorkoutDaysFragment.class);

    private Class<? extends Fragment> mFragmentClass;

    private NavigationLocation(Class<? extends Fragment> fragmentClass) {
        mFragmentClass = fragmentClass;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return mFragmentClass;
    }
}
