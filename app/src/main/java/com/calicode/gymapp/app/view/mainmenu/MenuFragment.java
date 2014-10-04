package com.calicode.gymapp.app.view.mainmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.view.BaseFragment;

public class MenuFragment extends BaseFragment implements OnClickListener {

    @Override
    protected boolean useProgressAndError() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        view.findViewById(R.id.lastFiveWorkoutsButton).setOnClickListener(this);
        view.findViewById(R.id.addWorkoutButton).setOnClickListener(this);
        view.findViewById(R.id.logoutButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lastFiveWorkoutsButton:
                navigate(NavigationLocation.WORKOUT_DAYS);
                break;
            case R.id.addWorkoutButton:
                break;
            case R.id.logoutButton:
                break;
        }
    }
}
