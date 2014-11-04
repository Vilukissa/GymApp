package com.calicode.gymapp.app.view.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.workout.days.WorkoutDaysModel;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

public class AddWorkoutCompletedFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.add_workout_completed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentProvider.get().getComponent(WorkoutDaysModel.class).clearCache();
                navigateBack();
            }
        });
        return view;
    }
}
