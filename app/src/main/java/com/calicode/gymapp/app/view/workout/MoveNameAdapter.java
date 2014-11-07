package com.calicode.gymapp.app.view.workout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.model.workout.movename.MoveNameData;

import java.util.ArrayList;
import java.util.List;

public class MoveNameAdapter extends ArrayAdapter<String> {

    private List<MoveNameData> mItems = new ArrayList<MoveNameData>();

    public static MoveNameAdapter create(Context context, List<MoveNameData> objects) {
        return new MoveNameAdapter(context, objects, buildMoveNameList(objects));
    }

    private static List<String> buildMoveNameList(List<MoveNameData> objects) {
        List<String> moveNames = new ArrayList<String>();
        for (MoveNameData moveNameData : objects) {
            moveNames.add(moveNameData.getName());
        }
        return moveNames;
    }

    private MoveNameAdapter(Context context, List<MoveNameData> items, List<String> moveNameList) {
        super(context, R.layout.move_name_item, R.id.moveNameItemTextView, moveNameList);
        mItems = items;
    }

    public MoveNameData getMoveNameData(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout = (LinearLayout) super.getView(position, convertView, parent);
        linearLayout.setPadding(0, 0, 0, 0);
        return linearLayout;
    }
}
