package com.example.mediarecod1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
//import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MyGlobals _myGlobals;
    private MediaRecorder rec;
    private MediaPlayer _mediaPlayer;
    boolean isRec = false;
    String filePath = "";
    Button btnRecord;
    private String fun = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //前の画面に戻る
        findViewById(R.id.buttonBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        _myGlobals = (MyGlobals) this.getApplication();
        rec = new MediaRecorder();
        _mediaPlayer = new MediaPlayer();
        btnRecord = findViewById(R.id.btnRecord);

        isRec = false;





        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //パーミッションを✓
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission
                        (MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO};
                    ActivityCompat.requestPermissions(MainActivity.this, permission, 1);
                    return;
                }
                //
                EditText et1 = (EditText) findViewById(R.id.editText);
                _myGlobals.name = et1.getText().toString();

                //内部ストレージに保存する際の名前設定
                filePath = getFilesDir().getPath() + "/" + _myGlobals.name + ".mp3";

                //録音終了時保存
                if (!isRec) {
                    rec.setAudioSource(MediaRecorder.AudioSource.MIC);
                    rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    rec.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    rec.setOutputFile(filePath);

                    Log.d("1", toString());


                    Log.e("nakagawaRec", filePath);
                    try {
                        rec.prepare();
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                    rec.start();
                    isRec = true;
                    btnRecord.setText("停止");
                } else {
                    rec.stop();
                    rec.reset();
                    isRec = false;
                    btnRecord.setText("録音");

                    Toast.makeText(MainActivity.this, "保存されました。", Toast.LENGTH_LONG).show();
                }

            }
        });

        //再生ボタンを押されたとき再生される
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText) findViewById(R.id.editText);
                _myGlobals.name = et1.getText().toString();

                filePath = getFilesDir().getPath() + "/" + _myGlobals.name + ".mp3";
                try {
                    //_mediaPlayer.reset();
                    //ここで再生している
                    _mediaPlayer.setDataSource(filePath);
                    _mediaPlayer.prepare();
                    _mediaPlayer.start();
//                    _mediaPlayer.setOnPreparedListener(new PlayerPreparedListener());
//                    _mediaPlayer.setOnCompletionListener(new PlayerCompletionListener());
//                    _mediaPlayer.prepareAsync();

                    

                    Log.d("2", toString());

                } catch (IOException ex) {
                    Log.e("nakagawa", ex.toString());
                }

            }
        });

        findViewById(R.id.btnstop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    _mediaPlayer.stop();
                    _mediaPlayer.reset();
                    _mediaPlayer.release();
                    _mediaPlayer = null;





            }
        });
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        rec.release();
//    }

    //アクセス修飾子
    //public protected 指定なし private
    class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Button btnPlay = findViewById(R.id.btn_play);
            btnPlay.setEnabled(true);

        }
    }

    class PlayerCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {


        }
    }
}

