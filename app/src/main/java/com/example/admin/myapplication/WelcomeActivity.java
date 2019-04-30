package com.example.admin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.admin.myapplication.utils.StateBarTranslucentUtils;
import com.example.admin.myapplication.view.CustomTutorialSupportFragment;
import com.orhanobut.logger.Logger;


public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        //状态栏透明
        StateBarTranslucentUtils.setStateBarTranslucent(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new CustomTutorialSupportFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume");
    }
}
