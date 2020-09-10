package com.ilikeckmrning.myclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;
    private TextView textViewScore;
    private boolean gameOver = false;
    private int clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewScore = findViewById(R.id.textViewScore);
        CountDownTimer timer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(getTime(millisUntilFinished));
                if (millisUntilFinished < 10000) {
                    textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }

            @Override
            public void onFinish() {
                gameOver = true;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                int max = preferences.getInt("max", 0);
                if (clicks >= max) {
                    preferences.edit().putInt("max", clicks).apply();
                }
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("result", clicks);
                startActivity(intent);
            }
        };
        timer.start();
    }
    private String getTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public void click(View view) {
        clicks++;
        textViewScore.setText("Your score is: " + clicks);
    }
}