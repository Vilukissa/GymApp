package com.calicode.gymapp.app.model.workout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WorkoutMove {

    @JsonProperty("moveId")
    private String mMoveId;

    @JsonProperty("moveNameId")
    private String mMoveNameId;

    @JsonProperty("moveName")
    private String mMoveName;

    @JsonProperty("setList")
    private List<WorkoutSet> mSetList = new ArrayList<WorkoutSet>();

    public String getMoveId() {
        return mMoveId;
    }

    public String getMoveNameId() {
        return mMoveNameId;
    }

    public String getMoveName() {
        return mMoveName;
    }

    public List<WorkoutSet> getSetList() {
        return mSetList;
    }

    private WorkoutMove() {}

    public static WorkoutMove build(String name, List<WorkoutSet> sets) {
        WorkoutMove move = new WorkoutMove();
        move.mMoveName = name;
        move.mSetList = sets;
        return move;
    }
}