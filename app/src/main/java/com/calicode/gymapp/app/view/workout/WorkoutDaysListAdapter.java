package com.calicode.gymapp.app.view.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.workout.WorkoutDaysData.WorkoutDay;

import java.util.List;

public class WorkoutDaysListAdapter extends BaseAdapter {

    private final List<WorkoutDay> mItems;
    private final Context mContext;

    public WorkoutDaysListAdapter(Context context, List<WorkoutDay> items) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item, viewGroup, false);
        }

        TextView itemTextView = (TextView) view.findViewById(R.id.listItemText);
        itemTextView.setText(mItems.get(i).getDay());

        return view;
    }
}
