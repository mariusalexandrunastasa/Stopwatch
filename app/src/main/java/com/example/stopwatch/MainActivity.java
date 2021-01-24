package com.example.stopwatch;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.atomic.AtomicLong;

public class MainActivity extends AppCompatActivity {

    Button buttonStart, buttonStop, buttonReset;
    Chronometer timer;
    ImageView loadingIcon;
    Animation rotate_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.StartButton);
        buttonStop = findViewById(R.id.StopButton);
        buttonReset = findViewById(R.id.ResetButton);
        loadingIcon = findViewById(R.id.loadingIcon);
        timer = findViewById(R.id.elapsedTime);
        rotate_logo = AnimationUtils.loadAnimation(this,R.anim.rotate_logo);

        AtomicLong base = new AtomicLong(0);

        buttonStop.setAlpha(0);
        loadingIcon.setVisibility(View.INVISIBLE);

        buttonStart.setOnClickListener(v -> {
            buttonStop.animate().alpha(1).translationY(-173).setDuration(300).start();
            buttonStart.animate().alpha(0).setDuration(300).start();
            loadingIcon.setVisibility(View.VISIBLE);
            loadingIcon.startAnimation(rotate_logo);
            timer.setBase(SystemClock.elapsedRealtime() + base.get() );
            timer.start();
            base.set(0);


            // notification implementation
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"Timer Notification");
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setUsesChronometer(true);
            builder.setSmallIcon(R.drawable.ic_baseline_add_alert_24);
            builder.setContentTitle("Stopwatch");
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        });

        buttonStop.setOnClickListener(v -> {
            buttonStop.animate().alpha(0).translationY(173).setDuration(300).start();
            buttonStart.animate().alpha(1).setDuration(300).start();
            loadingIcon.clearAnimation();
            loadingIcon.setVisibility(View.INVISIBLE);
            base.set(timer.getBase() - SystemClock.elapsedRealtime());
            timer.stop();
        });

        buttonReset.setOnClickListener(v -> {
            buttonStop.animate().alpha(0).translationY(173).setDuration(300).start();
            buttonStart.animate().alpha(1).setDuration(300).start();
            loadingIcon.clearAnimation();
            loadingIcon.setVisibility(View.INVISIBLE);
            timer.stop();
            timer.setBase(SystemClock.elapsedRealtime());
            base.set(0);
        });
    }
}