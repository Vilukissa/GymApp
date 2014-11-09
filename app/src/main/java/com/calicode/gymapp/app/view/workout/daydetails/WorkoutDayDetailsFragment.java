package com.calicode.gymapp.app.view.workout.daydetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.workout.WorkoutDay;
import com.calicode.gymapp.app.model.workout.days.WorkoutDaysModel;
import com.calicode.gymapp.app.model.workout.remove.RemoveWorkoutDayModel;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.NetworkRequestFragment;

public class WorkoutDayDetailsFragment extends NetworkRequestFragment {

    private static final String REMOVING_WORKOUT = "removing_workout";

    private WorkoutDayDetailsTaskModel mTaskModel;
    private MenuItem mRemoveWorkoutItem;
    private boolean mRemovingWorkout;

    @Override
    protected int getLayoutResource() {
        return R.layout.workout_day_details;
    }

    @Override
    public void errorOnClick() {
        removeWorkout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mTaskModel = ComponentProvider.get().createOrGetTaskComponent(WorkoutDayDetailsTaskModel.class);
        WorkoutDay workoutDay = mTaskModel.getSelectedWorkoutDay();

        setTextViewData(R.id.workoutDayDate, workoutDay.getDay());

        ListView moveList = (ListView) view.findViewById(R.id.workoutMoveList);
        moveList.setAdapter(new MoveListAdapter(getActivity(), workoutDay.getMoveList()));

        if (savedInstanceState != null) {
            mRemovingWorkout = savedInstanceState.getBoolean(REMOVING_WORKOUT);
        }

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(REMOVING_WORKOUT, mRemovingWorkout);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mRemoveWorkoutItem = menu.findItem(R.id.removeWorkout);
        mRemoveWorkoutItem.setEnabled(!mRemovingWorkout);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.workout_details_remove, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.removeWorkout) {
            removeWorkout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void removeWorkout() {
        if (mRemovingWorkout) {
            return;
        }

        mRemovingWorkout = true;
        if (mRemoveWorkoutItem != null) {
            mRemoveWorkoutItem.setEnabled(false);
        }

        showProgress();

        int workoutId = mTaskModel.getSelectedWorkoutDay().getWorkoutId();
        OperationHandle handle = ComponentProvider.get().getComponent(
                RemoveWorkoutDayModel.class).removeWorkout(workoutId);
        OnOperationCompleteListener listener = new OnOperationCompleteListener() {
            @Override
            public void onSuccess(Object data) {
                ComponentProvider.get().getComponent(WorkoutDaysModel.class).clearCache();
                navigateBack();
            }

            @Override
            public void onFailure(RequestError error) {
                mRemovingWorkout = false;
                if (mRemoveWorkoutItem != null) {
                    mRemoveWorkoutItem.setEnabled(true);
                }
                setErrorText(error.getErrorMessage());
                showError();
            }
        };
        attachPersistentListener(handle, listener);

    }
}
