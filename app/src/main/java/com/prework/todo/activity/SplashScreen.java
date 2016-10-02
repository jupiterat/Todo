package com.prework.todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.prework.todo.R;

/**
 * Created by duongthoai on 10/2/16.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                //
                Intent i = new Intent(SplashScreen.this, ToDoActivity.class);
                startActivity(i);
                //

            }
        }, 2000);

    }
}
