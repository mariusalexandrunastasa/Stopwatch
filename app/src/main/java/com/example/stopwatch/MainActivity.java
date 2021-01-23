package com.example.stopwatch;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicLong;

public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonStop, buttonReset;
    Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.StartButton);
        buttonStop = findViewById(R.id.StopButton);
        buttonReset = findViewById(R.id.ResetButton);
        timer = findViewById(R.id.elapsedTime);
        AtomicLong base = new AtomicLong(0);

        buttonStop.setAlpha(0);

        buttonStart.setOnClickListener(v -> {
            buttonStop.animate().alpha(1).translationY(-173).setDuration(300).start();
            buttonStart.animate().alpha(0).setDuration(300).start();
            timer.setBase(SystemClock.elapsedRealtime() + base.get());
            timer.start();
            base.set(0);
        });

        buttonStop.setOnClickListener(v -> {
            buttonStop.animate().alpha(0).translationY(173).setDuration(300).start();
            buttonStart.animate().alpha(1).setDuration(300).start();
            base.set(timer.getBase() - SystemClock.elapsedRealtime());
            timer.stop();
        });

        buttonReset.setOnClickListener(v -> {
            buttonStop.animate().alpha(0).translationY(173).setDuration(300).start();
            buttonStart.animate().alpha(1).setDuration(300).start();
            timer.stop();
            timer.setBase(SystemClock.elapsedRealtime());
            base.set(0);
        });

    }

}