    package com.example.mediarecod1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //Mainactivity 録音の画面に遷移
        findViewById(R.id.recod_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, MainActivity.class);
                        startActivity(intent);

                    }
                }
        );

        //SecondActivity 音声選択の画面に遷移
        findViewById(R.id.voice_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this, ListActivity.class);
                        startActivity(intent);

                    }
                }
        );
        //AutomaticVoice　自動音声の画面に遷移
        findViewById(R.id.automaticvoice_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Home.this,Bouhan.class);
                        startActivity(intent);
                    }
                }
        );
        //SesorActivity　センサ画面に遷移
        findViewById(R.id.sensa_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Home.this,SesorActivity.class);
                        startActivity(intent);



                    }
                }
        );

    }
}


