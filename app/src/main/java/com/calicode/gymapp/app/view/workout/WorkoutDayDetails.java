package com.calicode.gymapp.app.view.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.workout.WorkoutDaysData.WorkoutDay;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

public class WorkoutDayDetails extends BaseFragment {

    @Override
    protected boolean useProgressAndError() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.workout_day_details;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        WorkoutDayDetailsTaskModel taskModel = ComponentProvider.get().createOrGetTaskComponent(WorkoutDayDetailsTaskModel.class);
        WorkoutDay workoutDay = taskModel.getSelectedWorkoutDay();

        TextView title = (TextView) view.findViewById(R.id.workoutDayTitle);
        title.setText(workoutDay.getDay());

        View moveListHeader = view.findViewById(R.id.setListHeader);
        if (workoutDay.getMoveList().size() == 0) {
            moveListHeader.setVisibility(View.GONE);
        }

        ListView moveList = (ListView) view.findViewById(R.id.workoutMoveList);
        moveList.setAdapter(new MoveListAdapter(getActivity(), workoutDay.getMoveList()));

        return view;
    }
}
