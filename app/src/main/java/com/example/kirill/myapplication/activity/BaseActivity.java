package com.example.kirill.myapplication.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.kirill.myapplication.R;

public class BaseActivity extends AppCompatActivity {

    protected ViewGroup rootView;
    private FrameLayout loadingView;
    private RelativeLayout noConnectionView;

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

    public void showTryConnection(View.OnClickListener onClickListener) {
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        noConnectionView = (RelativeLayout) vi.inflate(R.layout.no_connection, null);
        rootView.addView(noConnectionView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Button retryBtn = (Button) noConnectionView.findViewById(R.id.retry);
        retryBtn.setOnClickListener(onClickListener);
    }

    public void hideTryConnection() {
        if (noConnectionView == null) {
            return;
        }
        rootView.removeView(noConnectionView);
        noConnectionView = null;
    }

}
