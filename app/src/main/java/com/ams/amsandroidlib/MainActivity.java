package com.ams.amsandroidlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ams.amsandroid.Hello;

public class MainActivity extends AppCompatActivity {
    Hello hello = new Hello();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hello.hey();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}