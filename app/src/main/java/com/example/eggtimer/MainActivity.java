package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlButton;
    CountDownTimer countdownTimer;
    boolean counterIsActive=false;
    ImageView image;

    public void updateTimer(int secondsLeft){
        int minutes= (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;

        String secondString=Integer.toString(seconds);
        if(seconds<10)
        {
            secondString="0"+Integer.toString(seconds);
        }

        timerTextView.setText(Integer.toString(minutes)+":" + secondString);
    }


    public void controlTimer(View view){
        //Log.i("Button Pressed","Yes");
        if(counterIsActive==false){
            counterIsActive=true;

            controlButton.setText("STOP!");
            timerSeekBar.setEnabled(false); //seekbar will be disabled when timer is counting down
            countdownTimer=new CountDownTimer(timerSeekBar.getProgress()*1000+100,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    //countdown is counting down(every second)
                    updateTimer((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    //countdown is finished(after 10 seconds)
                    timerTextView.setText("0:00");
                    MediaPlayer mplayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mplayer.start();
                   //image.setImageResource(R.drawable.jpeg);

                    //reset the timer
                    timerTextView.setText("0:30");;

                    timerSeekBar.setProgress(30);
                    controlButton.setText("GO!");
                    timerSeekBar.setEnabled(true);
                    counterIsActive=false;
                    //image.setImageResource(R.drawable.egg2);
                }
            }.start();

        }else{
            //reset the timer
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            controlButton.setText("GO!");
            timerSeekBar.setEnabled(true);
            counterIsActive=false;

            //stop the countdown timer
            countdownTimer.cancel();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar=(SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView=(TextView)findViewById(R.id.timerTextView);
        controlButton=(Button)findViewById(R.id.controller);
        image=(ImageView)findViewById(R.id.imageView);

        timerSeekBar.setMax(600); //10min or 600sec
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
