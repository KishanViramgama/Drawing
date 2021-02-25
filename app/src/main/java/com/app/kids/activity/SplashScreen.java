package com.app.kids.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.kids.R;
import com.app.kids.util.Method;

public class SplashScreen extends AppCompatActivity {

    private Boolean isCancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        Method method = new Method(SplashScreen.this);
        method.changeStatusBarColor();

        splash();

    }

    private void splash() {
        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(() -> {
            if (!isCancelled) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finishAffinity();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onDestroy() {
        isCancelled = true;
        super.onDestroy();
    }
}