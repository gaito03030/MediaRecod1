package com.example.mediarecod1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class okaeri extends AppCompatActivity {

    MyGlobals _myGlobals;
    MediaPlayer mediaPlayer;
    String filePath = "";

    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okaeri);

        mediaPlayer = new MediaPlayer();
        _myGlobals = (MyGlobals) this.getApplication();

        filePath =getFilesDir().getPath() + "/" + _myGlobals.select;


        Timer timer = new Timer(false);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(okaeri.this, Home.class);
                startActivity(intent);

                timer.cancel();
            }
        };
        timer.schedule(task, 4000);


        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}