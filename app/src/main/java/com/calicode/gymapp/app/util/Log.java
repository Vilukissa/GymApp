package com.calicode.gymapp.app.util;

import com.calicode.gymapp.app.GymApp;
import com.calicode.gymapp.app.R;

public class Log {

    public static void debug(String text) {
        android.util.Log.d(GymApp.sAppContext.getString(R.string.app_name), text);
    }

    public static void info(String text) {
        android.util.Log.i(GymApp.sAppContext.getString(R.string.app_name), text);
    }

    public static void error(String text) {
        android.util.Log.e(GymApp.sAppContext.getString(R.string.app_name), text);
    }

    public static void error(String text, Throwable throwable) {
        android.util.Log.e(GymApp.sAppContext.getString(R.string.app_name), text, throwable);
    }
}
