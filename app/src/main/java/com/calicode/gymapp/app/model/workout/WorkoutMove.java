package com.calicode.gymapp.app.model.workout;

import com.calicode.gymapp.app.model.workout.movename.MoveNameData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WorkoutMove {

    @JsonProperty("moveId")
    private String mMoveId;

    @JsonProperty("moveNameId")
    private int mMoveNameId;

    @JsonProperty("moveName")
    private String mMoveName;

    @JsonProperty("setList")
    private List<WorkoutSet> mSetList = new ArrayList<WorkoutSet>();

    public String getMoveId() {
        return mMoveId;
    }

    public int getMoveNameId() {
        return mMoveNameId;
    }

    public String getMoveName() {
        return mMoveName;
    }

    public List<WorkoutSet> getSetList() {
        return mSetList;
    }

    private WorkoutMove() {}

    public static WorkoutMove build(MoveNameData nameData, List<WorkoutSet> sets) {
        WorkoutMove move = new WorkoutMove();
        move.mMoveNameId = nameData.getNameId();
        move.mMoveName = nameData.getName();
        move.mSetList = sets;
        return move;
    }
}