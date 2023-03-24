package com.example.mediarecod1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class AutomaticVoice extends AppCompatActivity
        implements  TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    MyGlobals myGlobals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bouhan);

        myGlobals = (MyGlobals) this.getApplication();

        // TTS インスタンス生成
        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {

        // TTS初期化
        if (TextToSpeech.SUCCESS == status  && myGlobals.onsei == false
        ) {//かつONになっていたら
            Log.d("debug", "initialized");
            speechText();
        } else {
            Log.e("debug", "failed to initialize");

            Timer timer = new Timer(false);

            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    Intent intent = new Intent(AutomaticVoice.this, Home.class);
                    startActivity(intent);

                    timer.cancel();
                }
            };
            timer.schedule(task, 0500);
        }
    }

    private void shutDown(){
        if (null != tts) {
            // to release the resource of TextToSpeech
            tts.shutdown();
        }
    }

    private void speechText() {
        setSpeechRate();
        setSpeechPitch();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak("不審な人物を感知しました。", TextToSpeech.QUEUE_FLUSH, null, "messageID");
        }

        Timer timer = new Timer(false);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Intent intent = new Intent(AutomaticVoice.this, Home.class);
                startActivity(intent);

                timer.cancel();
            }
        };
        timer.schedule(task, 4000);

        setTtsListener();
    }

    // 読み上げのスピード
    private void setSpeechRate(){
        if (null != tts) {
            tts.setSpeechRate((float) 1.0);
        }
    }

    // 読み上げのピッチ
    private void setSpeechPitch(){
        if (null != tts) {
            tts.setPitch((float) 1.0);//音声の高さ
        }
    }

    // 読み上げの始まりと終わりを取得
    private void setTtsListener(){
        int listenerResult =
                0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            listenerResult = tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId) {
                    Log.d("debug","progress on Done " + utteranceId);
                }

                @Override
                public void onError(String utteranceId) {
                    Log.d("debug","progress on Error " + utteranceId);
                }

                @Override
                public void onStart(String utteranceId) {
                    Log.d("debug","progress on Start " + utteranceId);
                }
            });
        }

        if (listenerResult != TextToSpeech.SUCCESS) {
            Log.e("debug", "failed to add utterance progress listener");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        shutDown();
    }
}