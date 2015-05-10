package com.example.kirill.myapplication.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.kirill.myapplication.R;

public class BaseActivity extends AppCompatActivity {

    protected ViewGroup rootView;
    private FrameLayout loadingView;

    public void showLoading() {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loadingView = (FrameLayout) vi.inflate(R.layout.loading, null);
        rootView.addView(loadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void hideLoading() {
        if (loadingView == null) {
            return;
        }
        rootView.removeView(loadingView);
        loadingView = null;
    }

    public void showTryConnection() {

    }

    public void hideTryConnection() {

    }

}
