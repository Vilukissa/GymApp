package com.calicode.gymapp.app.model.workout;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutDaysData {

    @JsonProperty("workoutList")
    private List<WorkoutDay> mWorkoutList = new ArrayList<WorkoutDay>();

    public List<WorkoutDay> getWorkoutList() {
        return mWorkoutList;
    }

    public static class WorkoutDay {

        @JsonProperty("day")
        private Date mDay;

        @JsonProperty("workoutId")
        private String mWorkoutId;

        @JsonProperty("moveList")
        private List<WorkoutMove> mMoveList = new ArrayList<WorkoutMove>();

        public String getDay() {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            return df.format(mDay);
        }

        public String getWorkoutId() {
            return mWorkoutId;
        }

        public List<WorkoutMove> getMoveList() {
            return mMoveList;
        }
    }

    public static class WorkoutMove {

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
    }

    public static class WorkoutSet {

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
    }
}
