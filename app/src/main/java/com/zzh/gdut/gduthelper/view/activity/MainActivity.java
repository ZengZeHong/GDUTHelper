package com.zzh.gdut.gduthelper.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zzh.gdut.gduthelper.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void personInfo(View view) {
        Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
        startActivity(intent);
    }
}
