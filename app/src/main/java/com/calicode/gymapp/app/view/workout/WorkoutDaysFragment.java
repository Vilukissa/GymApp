package com.calicode.gymapp.app.view.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.workout.WorkoutDaysData;
import com.calicode.gymapp.app.model.workout.WorkoutDaysData.WorkoutDay;
import com.calicode.gymapp.app.model.workout.WorkoutDaysModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

public class WorkoutDaysFragment extends BaseFragment implements OnItemClickListener {

    private ListView mList;

    @Override
    protected int getLayoutResource() {
        return R.layout.workout_days;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mList = (ListView) view.findViewById(R.id.workoutList);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        showProgress();
        OperationHandle handle = ComponentProvider.get().getComponent(WorkoutDaysModel.class).fetchWorkoutDays();
        OnOperationCompleteListener listener = new OnOperationCompleteListener() {

            @Override
            public void onSuccess(Object data) {
                Log.debug("Workouts fetched");
                WorkoutDaysData workoutDaysData = (WorkoutDaysData) data;
                WorkoutDaysListAdapter adapter = new WorkoutDaysListAdapter(
                        getActivity(), workoutDaysData.getWorkoutList());
                mList.setAdapter(adapter);
                mList.setOnItemClickListener(WorkoutDaysFragment.this);
                showContent();
            }

            @Override
            public void onFailure(RequestError error) {
                Log.debug("ERRRO WORKOUTS NOT FETCHED");
                setErrorText(error.getErrorMessage());
                showError();
            }
        };

        attachListener(handle, listener);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ComponentProvider.get().createOrGetTaskComponent(WorkoutDayDetailsTaskModel.class).
                setSelectedWorkoutDay((WorkoutDay) adapterView.getItemAtPosition(position));
        navigate(NavigationLocation.WORKOUT_DAY_DETAILS);
    }
}
