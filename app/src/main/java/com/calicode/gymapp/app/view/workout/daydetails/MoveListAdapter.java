package com.calicode.gymapp.app.view.workout.daydetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.workout.WorkoutMove;
import com.calicode.gymapp.app.model.workout.WorkoutSet;

import java.util.List;

public class MoveListAdapter extends BaseAdapter {

    private final List<WorkoutMove> mItems;
    private final Context mContext;

    public MoveListAdapter(Context context, List<WorkoutMove> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.move_list_item, viewGroup, false);
        }

        WorkoutMove move = mItems.get(i);

        TextView name = (TextView) view.findViewById(R.id.moveName);
        name.setText(move.getMoveName());

        LinearLayout setListView = (LinearLayout) view.findViewById(R.id.setList);
        List<WorkoutSet> setList = move.getSetList();

        if (setListView.getChildCount() == 0) {
            buildSetListView(setList, setListView);

        } else if (setListView.getChildCount() < setList.size()) {
            updateSetListItemsAndRemoveRest(setList, setListView);

        } else if (setListView.getChildCount() > setList.size()) {
            updateSetListItemsAndAppendRest(setList, setListView);

        } else {
            updateSetListItems(setList, setListView);
        }

        return view;
    }

    private void updateSetListItems(List<WorkoutSet> setList, LinearLayout setListView) {
        for (int i = 0; i < setList.size(); ++i) {
            View setView = setListView.getChildAt(i);
            WorkoutSet set = setList.get(i);
            updateSet(setView, set);
        }
    }

    private void updateSetListItemsAndAppendRest(List<WorkoutSet> setList, LinearLayout setListView) {
        for (int i = 0; i < setList.size(); ++i) {
            View setView = setListView.getChildAt(i);
            WorkoutSet set = setList.get(i);
            boolean appendView = false;

            if (setView == null) {
                appendView = true;
                setView = LayoutInflater.from(mContext).inflate(R.layout.set_list_item, setListView, false);
            }

            updateSet(setView, set);

            if (appendView) {
                setListView.addView(setView);
            }
        }
    }

    private void updateSet(View setView, WorkoutSet set) {
        TextView setCount = (TextView) setView.findViewById(R.id.moveSetCount);
        TextView repCount = (TextView) setView.findViewById(R.id.moveRepCount);
        TextView weight = (TextView) setView.findViewById(R.id.moveWeight);

        setCount.setText(set.getSetCount());
        repCount.setText(set.getRepCount());
        weight.setText(set.getWeight());
    }

    private void updateSetListItemsAndRemoveRest(List<WorkoutSet> setList, LinearLayout setListView) {
        for (int i = 0; i < setListView.getChildCount(); ++i) {
            View setView = setListView.getChildAt(i);
            if (i >= setList.size()) {
                setListView.removeView(setView);
                continue;
            }

            WorkoutSet set = setList.get(i);
            updateSet(setView, set);
        }
    }

    private void buildSetListView(List<WorkoutSet> setList, LinearLayout setListView) {
        for (WorkoutSet set : setList) {
            View setView = LayoutInflater.from(mContext).inflate(R.layout.set_list_item, setListView, false);
            updateSet(setView, set);
            setListView.addView(setView);
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
