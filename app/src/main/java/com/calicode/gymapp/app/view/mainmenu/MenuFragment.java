package com.calicode.gymapp.app.view.mainmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.logout.LogoutModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

public class MenuFragment extends BaseFragment implements OnClickListener {

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
                navigateToLocation(NavigationLocation.WORKOUT_DAYS);
                break;
            case R.id.addWorkoutButton:
                navigateToLocation(NavigationLocation.ADD_WORKOUT);
                break;
            case R.id.logoutButton:
                ComponentProvider.get().getComponent(LogoutModel.class).logout();
                break;
        }
    }
}
