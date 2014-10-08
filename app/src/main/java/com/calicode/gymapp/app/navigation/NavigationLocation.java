package com.calicode.gymapp.app.navigation;

import android.support.v4.app.Fragment;

import com.calicode.gymapp.app.view.login.LoginFragment;
import com.calicode.gymapp.app.view.main.MainFragment;
import com.calicode.gymapp.app.view.mainmenu.MenuFragment;
import com.calicode.gymapp.app.view.workout.WorkoutDayDetailsFragment;
import com.calicode.gymapp.app.view.workout.WorkoutDaysFragment;

import java.util.Arrays;
import java.util.List;

public enum NavigationLocation {

    MAIN(MainFragment.class),
    LOGIN(LoginFragment.class, NavigationFlags.DONT_ADD_TO_STACK),
    MAIN_MENU(MenuFragment.class),
    WORKOUT_DAYS(WorkoutDaysFragment.class),
    WORKOUT_DAY_DETAILS(WorkoutDayDetailsFragment.class);

    public enum NavigationFlags {
        DONT_ADD_TO_STACK;
        // TODO: add REQUIRES_LOGIN
    }

    private final NavigationFlags[] mFlags;

    private final Class<? extends Fragment> mFragmentClass;

    private NavigationLocation(Class<? extends Fragment> fragmentClass) {
        mFragmentClass = fragmentClass;
        mFlags = new NavigationFlags[0];
    }

    private NavigationLocation(Class<? extends Fragment> fragmentClass, NavigationFlags... flags) {
        mFragmentClass = fragmentClass;
        mFlags = flags;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return mFragmentClass;
    }

    public List<NavigationFlags> getNavigationFlags() {
        return Arrays.asList(mFlags);
    }
}
