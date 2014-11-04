package com.calicode.gymapp.app.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calicode.gymapp.app.R;

public abstract class NetworkRequestFragment extends BaseFragment {

    private static final String IS_ERROR_VISIBLE = "is_error_visible";

    public abstract void errorOnClick();

    private View mContent;
    private View mProgress;
    private View mError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mContent = view.findViewById(R.id.contentLayout);
        mProgress = view.findViewById(R.id.progressLayout);
        mError = view.findViewById(R.id.errorLayout);
        mError.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                errorOnClick();
            }
        });

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(IS_ERROR_VISIBLE)) {
                showError();
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ERROR_VISIBLE, mError.getVisibility() == View.VISIBLE);
    }

    protected View getContentView() {
        return mContent;
    }

    @Override
    protected void setTextViewData(int textViewId, String text) {
        ((TextView) mContent.findViewById(textViewId)).setText(text);
    }

    protected void showContent() {
        mError.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
        mContent.setVisibility(View.VISIBLE);
    }

    protected void showError() {
        mContent.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
    }

    protected void showProgress() {
        mContent.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mProgress.setVisibility(View.VISIBLE);
    }

    /** Error TextView keeps its state during view's
     * recreation because android:freezesText is set to 'true'*/
    protected void setErrorText(String errorText) {
        ((TextView) mError.findViewById(R.id.errorText)).setText(
                getString(R.string.common_error_title) + ": " + errorText);
    }
}
