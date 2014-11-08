package com.calicode.gymapp.app.view.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.OperationHandle;
import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.model.workout.WorkoutSet;
import com.calicode.gymapp.app.model.workout.add.AddWorkoutDayModel;
import com.calicode.gymapp.app.model.workout.movename.MoveNameData;
import com.calicode.gymapp.app.model.workout.movename.MoveNameModel;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.network.JsonOperation.OnOperationCompleteListener;
import com.calicode.gymapp.app.network.RequestError;
import com.calicode.gymapp.app.util.Formatter;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.NetworkRequestFragment;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutFragment extends NetworkRequestFragment implements OnClickListener {

    private static final String ERROR_TYPE = "error_type";
    private static final String FIRST_FETCH = "first_fetch";
    private static final int WORKOUT_MOVE_COUNT_LIMIT = 20;

    private enum ErrorType {
        MOVE_NAME,
        ADD_WORKOUT;
    }

    private AddWorkoutTaskModel mTaskModel;
    private List<MoveNameData> mMoveNameList = new ArrayList<MoveNameData>();
    private LinearLayout mMovesContainer;
    private ErrorType mErrorType;
    private boolean mFirstFetch;

    private OnClickListener mSetRowAddButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View setRowAddButton) {
            LinearLayout moveRowItem = (LinearLayout) setRowAddButton.getTag();
            LinearLayout setRowContainer = (LinearLayout) moveRowItem.findViewById(R.id.setRowContainer);
            int setRowItemCount = setRowContainer.getChildCount();

            if (setRowItemCount < WORKOUT_MOVE_COUNT_LIMIT) {
                // Show the Remove set button
                if (setRowItemCount == 1) {
                    View removeSetButton = setRowContainer.getChildAt(0).findViewById(R.id.removeSetButton);
                    removeSetButton.setVisibility(View.VISIBLE);
                    removeSetButton.setOnClickListener(mSetRowRemoveButtonOnClickListener);
                }
                // Hide the Add set button
                View addSetButton = setRowContainer.getChildAt(0).findViewById(R.id.addSetButton);
                addSetButton.findViewById(R.id.addSetButton).setVisibility(View.INVISIBLE);

                addSetRowItem(moveRowItem);
            }
        }
    };

    private OnClickListener mSetRowRemoveButtonOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View setRowRemoveButton) {
            LinearLayout moveRowItem = (LinearLayout) setRowRemoveButton.getTag();
            LinearLayout setRowContainer = (LinearLayout) moveRowItem.findViewById(R.id.setRowContainer);
            if (setRowContainer != null) {
                int setRowItemCount = setRowContainer.getChildCount();
                View setRowItem = (View) setRowRemoveButton.getParent().getParent();

                if (setRowItem != null && setRowItemCount > 1) {
                    // Last set removed so show Add button in previous set view
                    if (setRowItem.equals(setRowContainer.getChildAt(setRowContainer.getChildCount()-1))) {
                        setRowContainer.getChildAt(setRowContainer.getChildCount()-2).findViewById(R.id.addSetButton)
                                .setVisibility(View.VISIBLE);
                    }

                    // Removes itself from the set list container
                    setRowContainer.removeView(setRowItem);

                    // Count before removeView call so it's now 1
                    // Hide last item's Remove button
                    if (setRowItemCount == 2) {
                        View removeSetButton = setRowContainer.getChildAt(0).findViewById(R.id.removeSetButton);
                        removeSetButton.setVisibility(View.INVISIBLE);
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
    public void errorOnClick() {
        if (mErrorType == ErrorType.ADD_WORKOUT) {
            showContent();
        } else if (mErrorType == ErrorType.MOVE_NAME) {
            fetchMoveNames();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mTaskModel = ComponentProvider.get().createOrGetTaskComponent(AddWorkoutTaskModel.class);
        mMovesContainer = (LinearLayout) view.findViewById(R.id.workoutMoveRowContainer);

        view.findViewById(R.id.addMoveButton).setOnClickListener(this);
        view.findViewById(R.id.addWorkoutButton).setOnClickListener(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ERROR_TYPE)) {
                mErrorType = (ErrorType) savedInstanceState.getSerializable(ERROR_TYPE);
            }
        } else {
            mFirstFetch = true;
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchMoveNames();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mErrorType != null) {
            outState.putSerializable(ERROR_TYPE, mErrorType);
        }
        outState.putBoolean(FIRST_FETCH, mFirstFetch);
        if (!mFirstFetch) {
            mTaskModel.setWorkoutMoveList(buildMoveList());
        }
    }

    private void fetchMoveNames() {
        showProgress();

        OperationHandle handle = ComponentProvider.get().getComponent(MoveNameModel.class).fetchMoveNames();
        OnOperationCompleteListener listener = new OnOperationCompleteListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                mErrorType = null;
                mMoveNameList = (List<MoveNameData>) data;
                if (mFirstFetch) {
                    mFirstFetch = false;
                    addMoveRowItem();
                } else {
                    buildUiFromExistingData();
                }
                showContent();
            }

            @Override
            public void onFailure(RequestError error) {
                mErrorType = ErrorType.MOVE_NAME;
                setErrorText(error.getErrorMessage());
                showError();
            }
        };
        attachListener(handle, listener);
    }

    private void buildUiFromExistingData() {
        List<WorkoutMove> workoutMoveList = mTaskModel.getWorkoutMoveList();
        for (WorkoutMove workoutMove : workoutMoveList) {
            addMoveRowItem(workoutMove);
        }
    }

    private void addMoveRowItem() {
        addMoveRowItem(null);
    }

    private void addMoveRowItem(WorkoutMove workoutMove) {
        View moveRowItem = LayoutInflater.from(getActivity())
                .inflate(R.layout.add_workout_container_item, mMovesContainer, false);

        if (moveRowItem != null) {
            Spinner moveNameSpinner = (Spinner) moveRowItem.findViewById(R.id.moveNameSpinner);
            MoveNameAdapter adapter = MoveNameAdapter.create(getActivity(), mMoveNameList);
            moveNameSpinner.setAdapter(adapter);

            if (workoutMove != null) {
                int spinnerPosition = adapter.findPosition(workoutMove);
                moveNameSpinner.setSelection(spinnerPosition);

                int setCount = workoutMove.getSetList().size();
                for (WorkoutSet set : workoutMove.getSetList()) {
                    addSetRowItem((LinearLayout) moveRowItem, set, setCount);
                }
            } else {
                // Add only one set item
                addSetRowItem((LinearLayout) moveRowItem);
            }
            mMovesContainer.addView(moveRowItem);
        }
    }

    private void addSetRowItem(LinearLayout moveRowItem) {
        addSetRowItem(moveRowItem, null, 0);
    }

    private void addSetRowItem(LinearLayout moveRowItem, WorkoutSet workoutSet, int setCount) {
        LinearLayout setRowContainer = (LinearLayout) moveRowItem.findViewById(R.id.setRowContainer);
        View setRowItem = LayoutInflater.from(getActivity()).inflate(R.layout.set_item, setRowContainer, false);

        if (setRowItem != null) {
            if (workoutSet != null) {
                TextView setTextView = (TextView) setRowItem.findViewById(R.id.moveSetCountEditText);
                TextView repTextView = (TextView) setRowItem.findViewById(R.id.moveRepCountEditText);
                TextView weightTextView = (TextView) setRowItem.findViewById(R.id.moveWeightEditText);

                setTextView.setText(workoutSet.getSetCount());
                repTextView.setText(workoutSet.getRepCount());
                weightTextView.setText(workoutSet.getWeight());
            }

            View addSetButton = setRowItem.findViewById(R.id.addSetButton);
            addSetButton.setOnClickListener(mSetRowAddButtonOnClickListener);
            addSetButton.setTag(moveRowItem);

            View removeSetButton = setRowItem.findViewById(R.id.removeSetButton);
            removeSetButton.setTag(moveRowItem);

            // Hide Add button from previous set item and show Remove button from new set item
            if (setRowContainer.getChildCount() > 0) {
                setRowContainer.getChildAt(setRowContainer.getChildCount()-1)
                        .findViewById(R.id.addSetButton).setVisibility(View.INVISIBLE);
                removeSetButton.setOnClickListener(mSetRowRemoveButtonOnClickListener);
                removeSetButton.setVisibility(View.VISIBLE);

            } else if (setCount > 1) {
                removeSetButton.setOnClickListener(mSetRowRemoveButtonOnClickListener);
                removeSetButton.setVisibility(View.VISIBLE);
            }

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
                mErrorType = null;
                ComponentProvider.get().destroyTaskComponent(AddWorkoutTaskModel.class);
                navigateToLocation(NavigationLocation.ADD_WORKOUT_COMPLETED);
            }

            @Override
            public void onFailure(RequestError error) {
                mErrorType = ErrorType.ADD_WORKOUT;
                setErrorText(error.getErrorMessage());
                showError();
            }
        };
        attachPersistentListener(handle, listener);
    }

    private List<WorkoutMove> buildMoveList() {
        List<WorkoutMove> moves = new ArrayList<WorkoutMove>();
        int moveCount = mMovesContainer.getChildCount();

        for (int moveIndex = 0; moveIndex < moveCount; ++moveIndex) {
            LinearLayout moveRowItem = (LinearLayout) mMovesContainer.getChildAt(moveIndex);
            LinearLayout setRowContainer = (LinearLayout) moveRowItem.findViewById(R.id.setRowContainer);

            List<WorkoutSet> sets = new ArrayList<WorkoutSet>();
            int setCount = setRowContainer.getChildCount();

            for (int setIndex = 0; setIndex < setCount; ++setIndex) {
                View setItem = setRowContainer.getChildAt(setIndex);
                TextView setCountField = (TextView) setItem.findViewById(R.id.moveSetCountEditText);
                TextView repCountField = (TextView) setItem.findViewById(R.id.moveRepCountEditText);
                TextView weightField = (TextView) setItem.findViewById(R.id.moveWeightEditText);

                WorkoutSet set = WorkoutSet.build(
                        setCountField.getText().toString(),
                        repCountField.getText().toString(),
                        weightField.getText().toString());
                sets.add(set);
            }

            Spinner moveNameSpinner = (Spinner) moveRowItem.findViewById(R.id.moveNameSpinner);
            int selectedPosition = moveNameSpinner.getSelectedItemPosition();
            MoveNameData moveNameData = ((MoveNameAdapter) moveNameSpinner.getAdapter())
                    .getMoveNameData(selectedPosition);
            WorkoutMove move = WorkoutMove.build(moveNameData, sets);
            moves.add(move);
        }

        return moves;
    }
}
