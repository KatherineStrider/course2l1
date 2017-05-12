package com.example.kate.course2l1;

import android.app.Application;
import android.util.Log;

import com.example.kate.course2l1.SQLite.DBEmployee;

/**
 * Created by Kate on 12.05.2017.
 */

public class App extends Application {

    private static DBEmployee dbEmployee = null;
    public static final String LOG_TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {

        super.onCreate();

        dbEmployee = new DBEmployee(getApplicationContext());
    }

    public static DBEmployee getDB() {
        return dbEmployee;
    }

    public static void Log(String log) {
        Log.d(LOG_TAG, log);
    }
}
