package com.calicode.gymapp.app.view.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.model.workout.WorkoutSet;
import com.calicode.gymapp.app.model.workout.add.AddWorkoutDayModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.Formatter;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutFragment extends BaseFragment implements OnClickListener {

    private LinearLayout mMovesContainer;
    private OnClickListener mSetRowAddButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View setRowAddButton) {
            LinearLayout moveRowItem = (LinearLayout) setRowAddButton.getTag();
            int setRowItemCount = ((LinearLayout) moveRowItem.findViewById(R.id.setRowContainer)).getChildCount();

            setRowAddButton.setOnClickListener(mSetWorRemoveButtonOnClickListener);
            ((Button) setRowAddButton).setText("-");

            if (setRowItemCount < 8) {
                addSetRowItem(moveRowItem);
            }
        }
    };

    private OnClickListener mSetWorRemoveButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View setRowRemoveButton) {
            LinearLayout moveRowItem = (LinearLayout) setRowRemoveButton.getTag();
            LinearLayout setRowContainer = (LinearLayout) moveRowItem.findViewById(R.id.setRowContainer);
            if (moveRowItem != null && setRowContainer != null) {
                int setRowItemCount = setRowContainer.getChildCount();
                View setRowItem = (View) setRowRemoveButton.getParent();

                if (setRowItemCount > 1) {
                    String moveName = ((TextView) setRowItem.findViewById(R.id.moveNameEditText)).getText().toString();
                    EditText firstViewBeforeRemove = (EditText) setRowContainer.getChildAt(0)
                            .findViewById(R.id.moveNameEditText);

                    setRowContainer.removeView(setRowItem);

                    EditText firstViewAfterRemove = (EditText) setRowContainer.getChildAt(0)
                            .findViewById(R.id.moveNameEditText);

                    firstViewAfterRemove.setVisibility(View.VISIBLE);

                    if (!firstViewBeforeRemove.equals(firstViewAfterRemove)) {
                        firstViewAfterRemove.setText(moveName);
                    }
                }
            }
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.add_workout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mMovesContainer = (LinearLayout) view.findViewById(R.id.workoutMoveRowContainer);
        addMoveRowItem();

        view.findViewById(R.id.addMoveButton).setOnClickListener(this);
        view.findViewById(R.id.addWorkoutButton).setOnClickListener(this);

        return view;
    }

    private void addMoveRowItem() {
        View moveRowItem = LayoutInflater.from(getActivity())
                .inflate(R.layout.add_workout_container_item, mMovesContainer, false);

        if (moveRowItem != null) {
            addSetRowItem((LinearLayout) moveRowItem);
            mMovesContainer.addView(moveRowItem);
        }
    }

    private void addSetRowItem(LinearLayout moveRowItem) {
        LinearLayout setRowContainer = (LinearLayout) moveRowItem.findViewById(R.id.setRowContainer);
        View setRowItem = LayoutInflater.from(getActivity()).inflate(R.layout.set_item, setRowContainer, false);

        if (setRowItem != null) {
            if (setRowContainer.getChildCount() > 0) {
                setRowItem.findViewById(R.id.moveNameEditText).setVisibility(View.INVISIBLE);
            }
            View addSetButton = setRowItem.findViewById(R.id.addSetButton);
            addSetButton.setOnClickListener(mSetRowAddButtonOnClickListener);
            addSetButton.setTag(moveRowItem);
            setRowContainer.addView(setRowItem);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addMoveButton:
                addMoveRowItem();
                break;
            case R.id.addWorkoutButton:
                addWorkout();
                break;
        }
    }

    private void addWorkout() {
        showProgress();

        // TODO: day field
        String day = Formatter.changeDateStringFormat("28.10.2014",
                Formatter.DAY_MONTH_YEAR_WITH_DOTS, Formatter.YEAR_MONTH_DAY_WITH_DASHES);
        List<WorkoutMove> moves = buildMoveList();

        OperationHandle handle = ComponentProvider.get().getComponent(AddWorkoutDayModel.class).addWorkout(day, moves);
        OnOperationCompleteListener listener = new OnOperationCompleteListener() {
            @Override
            public void onSuccess(Object data) {
                navigateToLocation(NavigationLocation.ADD_WORKOUT_COMPLETED);
            }

            @Override
            public void onFailure(RequestError error) {
                setErrorText(error.getErrorMessage());
                showError();
            }
        };
        attachPersistentListener(handle, listener);
    }

    private List<WorkoutMove> buildMoveList() {
        List<WorkoutMove> moves = new ArrayList<WorkoutMove>();
        int moveCount = mMovesContainer.getChildCount();

        for (int i = 0; i < moveCount; ++i) {
            LinearLayout moveItem = (LinearLayout) mMovesContainer.getChildAt(i).findViewById(R.id.setRowContainer);

            List<WorkoutSet> sets = new ArrayList<WorkoutSet>();
            int setCount = moveItem.getChildCount();
            TextView nameField = null;

            for (int z = 0; z < setCount; ++z) {
                View setItem = moveItem.getChildAt(z);
                if (z == 0) {
                    nameField = (TextView) setItem.findViewById(R.id.moveNameEditText);
                }
                TextView setCountField = (TextView) setItem.findViewById(R.id.moveSetCountEditText);
                TextView repCountField = (TextView) setItem.findViewById(R.id.moveRepCountEditText);
                TextView weightField = (TextView) setItem.findViewById(R.id.moveWeightEditText);

                WorkoutSet set = WorkoutSet.build(
                        setCountField.getText().toString(),
                        repCountField.getText().toString(),
                        weightField.getText().toString());
                sets.add(set);
            }
            WorkoutMove move = WorkoutMove.build(
                    nameField.getText().toString(),
                    sets);
            moves.add(move);
        }

        return moves;
    }
}
