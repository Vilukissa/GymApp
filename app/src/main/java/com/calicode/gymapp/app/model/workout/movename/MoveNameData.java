package com.calicode.gymapp.app.model.workout.movename;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveNameData {

    @JsonProperty("moveNameId")
    private int mNameId = -1;

    @JsonProperty("moveName")
    private String mName;

    public int getNameId() {
        return mNameId;
    }

    public String getName() {
        return mName;
    }
}
