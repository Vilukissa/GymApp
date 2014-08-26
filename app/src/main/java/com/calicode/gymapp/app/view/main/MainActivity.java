package com.calicode.gymapp.app.view.main;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.calicode.gymapp.app.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }
}
