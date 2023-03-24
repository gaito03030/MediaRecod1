package com.example.mediarecod1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SesorActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    //private TextView textView;

    Handler handler = new Handler();

    boolean wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensoractivity);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        wait_time15();

        findViewById(R.id.buttonBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    @Override
    protected void onResume(){
        super.onResume();

        Sensor accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_NORMAL);

        // textView = findViewById(R.id.text_view);
    }
    @Override
    protected void onPause(){
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            if(wait == true) {
                float sensorX = event.values[0];
                //引き戸（しょうじ）
                float sensorY = event.values[1];
                //スマホ縦向き
                float sensorZ = event.values[2];
                //開き戸（ドア）


                String strX = "加速度センサーX" + sensorX + "\n";
                String strY = "加速度センサーY" + sensorY + "\n";
                String strZ = "加速度センサーZ" + sensorZ + "\n";

                //スマホが縦になったら
                if (sensorY >= 9) {
                    //扉が開いたら
                    if (sensorZ >= 2) {
                        Log.d("LOG1", "開きました");
                        //録音開始
                        Intent intent = new Intent(SesorActivity.this, VoiceActivity.class);
                        startActivity(intent);
                    }
                }
            }
            //textView.setText(strX + strY + strZ );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void wait_time15() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                wait = true;
            }
        }, 1000);
    }
}