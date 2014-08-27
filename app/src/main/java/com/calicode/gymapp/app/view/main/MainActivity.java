package com.calicode.gymapp.app.view.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.util.componentprovider.ComponentProvider;


public class MainActivity extends FragmentActivity {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ComponentProvider.get().destroyComponents();
        }
    }
}
