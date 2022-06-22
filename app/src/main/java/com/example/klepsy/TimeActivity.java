package com.example.klepsy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    Button pause_resume, exit_activity; // кнопочки
    TextView text_timer;
    private CountDownTimer mcountDownTimer;

    private boolean work_time_how = true;

    private boolean mTimerRunning;
    public int work_time;
    public int break_time;
    public int big_break_time;
    public int big_break_before;
    public int actual_circle = 0;
    private long mTimeLeftInMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);

        text_timer = (TextView) findViewById(R.id.text_timer);
        pause_resume = (Button) findViewById(R.id.pause_resume_button);
        exit_activity = (Button) findViewById(R.id.exit_activity_button);



        Intent intent = getIntent();

        work_time = intent.getIntExtra("work_time", 0) + 1000;
        break_time = intent.getIntExtra("break_time", 0) + 1000;
        big_break_time = intent.getIntExtra("big_break_time", 0) + 1000;
        big_break_before = intent.getIntExtra("big_break_before", 0);
        mTimeLeftInMillis = work_time - 1000;
        updateCountDownText();

        pause_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        exit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manag = getSupportFragmentManager();
                dialogExit dExit = new dialogExit();
                dExit.show(manag, "ExitAct");
            }
        });
    }

    private void pauseTimer() {
        mcountDownTimer.cancel();
        mTimerRunning = false;
        pause_resume.setText("ПРОДОЛЖИТЬ");
    }

    private void startTimer(){
        mcountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                final MediaPlayer doundStop = MediaPlayer.create(TimeActivity.this, R.raw.ustrt);
                if (work_time_how) {
                    actual_circle++;
                    if (actual_circle == big_break_before & big_break_before != 0) {
                        mTimeLeftInMillis = big_break_time;
                        actual_circle = 0;
                    } else {
                        mTimeLeftInMillis = break_time;
                    }
                    work_time_how = false;
                    doundStop.start();

                } else {

                    mTimeLeftInMillis = work_time;
                    work_time_how = true;
                    doundStop.start();
                }
                startTimer();
            }

        }.start();

        mTimerRunning = true;
        pause_resume.setText("ПАУЗА");

    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),
                "%02d:%02d", minutes, seconds);

        text_timer.setText(timeLeftFormatted);
    }
    @Override
    public void onBackPressed() {
        FragmentManager manag = getSupportFragmentManager();
        dialogExit dExit = new dialogExit();
        dExit.show(manag, "ExitAct");
    }
    public void escapeActivity() {
        pauseTimer();

        finish();
    }

}
