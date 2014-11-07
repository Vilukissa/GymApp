package com.calicode.gymapp.app.view.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.calicode.gymapp.app.util.Log;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;
import com.calicode.gymapp.app.view.NetworkRequestFragment;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutFragment extends NetworkRequestFragment implements OnClickListener {

    private static final String ERROR_TYPE = "error_type";
    private static final String FIRST_FETCH = "first_fetch";


    private enum ErrorType {
        MOVE_NAME,
        ADD_WORKOUT
    }

    private List<MoveNameData> mMoveNameList = new ArrayList<MoveNameData>();
    private LinearLayout mMovesContainer;
    private ErrorType mErrorType;
    private boolean mFirstFetch;

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
                View setRowItem = (View) setRowRemoveButton.getParent().getParent();

                if (setRowItem != null && setRowItemCount > 1) {
                    int moveNameSpinnerPosition = ((Spinner) setRowItem.findViewById(R.id.moveNameSpinner)).getSelectedItemPosition();
                    Spinner firstViewBeforeRemove = (Spinner) setRowContainer.getChildAt(0)
                            .findViewById(R.id.moveNameSpinner);

                    setRowContainer.removeView(setRowItem);
                    Spinner firstViewAfterRemove = (Spinner) setRowContainer.getChildAt(0)
                            .findViewById(R.id.moveNameSpinner);

                    firstViewAfterRemove.setVisibility(View.VISIBLE);

                    if (!firstViewBeforeRemove.equals(firstViewAfterRemove)) {
                        firstViewAfterRemove.setSelection(moveNameSpinnerPosition);
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
                    addMoveRowItem();
                } else {
                    // TODO: return previously added items
                    Log.error("NOT IMPLEMENTED YET!");
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
            Spinner moveNameSpinner = (Spinner) setRowItem.findViewById(R.id.moveNameSpinner);
            if (setRowContainer.getChildCount() > 0) {
                moveNameSpinner.setVisibility(View.GONE);
            }

            moveNameSpinner.setAdapter(MoveNameAdapter.create(getActivity(), mMoveNameList));

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
                mErrorType = null;
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

        for (int i = 0; i < moveCount; ++i) {
            LinearLayout moveItem = (LinearLayout) mMovesContainer.getChildAt(i).findViewById(R.id.setRowContainer);

            List<WorkoutSet> sets = new ArrayList<WorkoutSet>();
            int setCount = moveItem.getChildCount();
            Spinner moveNameSpinner = null;

            for (int z = 0; z < setCount; ++z) {
                View setItem = moveItem.getChildAt(z);
                if (z == 0) {
                    moveNameSpinner = (Spinner) setItem.findViewById(R.id.moveNameSpinner);
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

            int selectedPosition = moveNameSpinner.getSelectedItemPosition();
            MoveNameData moveNameData = ((MoveNameAdapter) moveNameSpinner.getAdapter())
                    .getMoveNameData(selectedPosition);
            WorkoutMove move = WorkoutMove.build(moveNameData, sets);
            moves.add(move);
        }

        return moves;
    }
}
