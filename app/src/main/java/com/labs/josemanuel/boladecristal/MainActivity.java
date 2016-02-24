package com.labs.josemanuel.boladecristal;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.labs.josemanuel.boladecristal.ShakeDetector.OnShakeListener;

public class MainActivity extends AppCompatActivity {

    private TextView answerLabel;
    private SensorManager mSensorManager;
    private Sensor mAcelerometer;
    private ShakeDetector mShakeDetector;

    final static float TRANSPARENTE = 0;
    final static float OPACO = 1;
    final static long DURATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answerLabel = (TextView) findViewById(R.id.txt_Respuesta);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAcelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mShakeDetector = new ShakeDetector(new OnShakeListener() {

            @Override
            public void onShake() {
                showAnswer();
            }
        });


    }

    public void questionAction(View view) {

        showAnswer();

    }

    public void showAnswer() {
        answerLabel.setText(MagicBall.getPrediction(this));
        //background.setBackgroundColor(MagicBall.getColor());
        changeBackgroundColor(this);
        animateBall();
        animateAnswer();
        playSound();
    }


    public void changeBackgroundColor(MainActivity view) {
        View factLayout = findViewById(R.id.relativeLayout);
        Button factBtnText = (Button) findViewById(R.id.btn_Question);
        int color = MagicBall.getColor(this);
        factLayout.setBackgroundColor(color);
        factBtnText.setTextColor(color);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAcelerometer, mSensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mShakeDetector);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
    }


    private void animateBall(){
        // tif(!animation.isRunning()) {
        ImageView imagen = (ImageView) findViewById(R.id.imageView);
        imagen.setImageResource(R.drawable.ball_animation);
        AnimationDrawable animation = (AnimationDrawable) imagen.getDrawable();
        if(animation.isRunning()) {
            animation.stop();
        }
        animation.start();

    }

    private void animateAnswer(){
        AlphaAnimation animation = new AlphaAnimation(TRANSPARENTE, OPACO);
        animation.setDuration(DURATION);
        animation.setFillAfter(true);
        answerLabel.setAnimation(animation);
    }

    private void playSound(){
        MediaPlayer player = MediaPlayer.create(this, R.raw.magic_ball);
        player.start();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            }
        });
    }


}





