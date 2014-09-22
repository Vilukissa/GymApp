package com.calicode.gymapp.app.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calicode.gymapp.app.R;
import com.calicode.gymapp.app.navigation.NavigationLocation;
import com.calicode.gymapp.app.view.BaseFragment;

public class LoginFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout1, null, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateBack();
            }
        });
        return view;
    }
}
