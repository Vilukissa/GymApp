package com.calicode.gymapp.app;

import android.app.Application;
import android.content.Context;

public class GymApp extends Application {

    public static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
    }
}
