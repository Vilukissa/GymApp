package com.calicode.gymapp.app.model.workout.movename;

import com.android.volley.Request.Method;
import com.calicode.gymapp.app.model.workout.movename.MoveNameDataParser.MoveNameDataJson;
import com.calicode.gymapp.app.network.JsonOperation;

public class MoveNameOperation extends JsonOperation {

    public MoveNameOperation() {
        super(Method.GET, new MoveNameDataParser(MoveNameDataJson.class));
    }

    @Override
    public String getUrl() {
        return "/v1/moves";
    }
}
