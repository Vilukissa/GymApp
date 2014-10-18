package com.calicode.gymapp.app.model.workout;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkoutSet {

    @JsonProperty("setId")
    private String mSetId;

    @JsonProperty("sets")
    private String mSetCount;

    @JsonProperty("reps")
    private String mRepCount;

    @JsonProperty("weight")
    private String mWeight;

    public String getSetId() {
        return mSetId;
    }

    public String getSetCount() {
        return mSetCount;
    }

    public String getRepCount() {
        return mRepCount;
    }

    public String getWeight() {
        return mWeight;
    }

    private WorkoutSet() {}

    public static WorkoutSet build(String setCount, String repCount, String weight) {
        WorkoutSet set = new WorkoutSet();
        set.mSetCount = setCount;
        set.mRepCount = repCount;
        set.mWeight = weight;
        return set;
    }
}
