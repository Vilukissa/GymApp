package com.calicode.gymapp.app.model.workout.movename;

import com.calicode.gymapp.app.network.JsonParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectReader;

import java.util.ArrayList;
import java.util.List;

public class MoveNameDataParser extends JsonParser {

    public MoveNameDataParser(Class<?> clazz) {
        super(clazz);
    }

    protected static final class MoveNameDataJson {
        @JsonProperty("moves")
        private List<MoveNameData> mMoveNameList = new ArrayList<MoveNameData>();
    }

    @Override
    public Object parseObject(ObjectReader objectReader, String json, Class<?> dataClass) throws Exception {
        MoveNameDataJson moveNameDataJson = (MoveNameDataJson) super.parseObject(objectReader, json, dataClass);
        return moveNameDataJson.mMoveNameList;
    }
}
