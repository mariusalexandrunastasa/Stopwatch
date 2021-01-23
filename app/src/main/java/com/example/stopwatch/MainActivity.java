package com.example.stopwatch;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.StartButton);
        buttonStop = findViewById(R.id.StopButton);

        buttonStop.setAlpha(0);

        buttonStart.setOnClickListener(v -> {
            buttonStop.animate().alpha(1).translationY(-173).setDuration(300).start();
            buttonStart.animate().alpha(0).setDuration(300).start();
        });

        buttonStop.setOnClickListener(v -> {
            buttonStop.animate().alpha(0).translationY(173).setDuration(300).start();
            buttonStart.animate().alpha(1).setDuration(300).start();
        });

    }



}