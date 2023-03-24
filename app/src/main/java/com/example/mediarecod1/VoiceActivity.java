package com.example.mediarecod1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VoiceActivity extends MainActivity {

    MyGlobals _myGlobals;
    private TextView textView, textView2;
    private Button button;
    //任意の識別番号
    private static final int REQUEST_CODE = 12345;
    private Timer timer;
    boolean flag = false;
    String filePath = "";
    private MediaPlayer _mediaPlayer;

    //アプリが開始されると最初に処理されるメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _myGlobals = (MyGlobals) this.getApplication();

        //このアプリで使用するレイアウトファイルをセット
        setContentView(R.layout.voiceactivity);

        //画面の上にあるアクションバーを隠す（隠さなくても良い）
        //    ActionBar actionBar = getSupportActionBar();
        //  actionBar.hide();

        //レイアウトにある２つのテキストビューを取得
        //textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
       /* //レイアウトにあるボタンを取得
        button = (Button) findViewById(R.id.button);

        //上で取得したボタンにクリックイベントを実装
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
        //音声認識用のインテントを作成
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //認識する言語を指定（この場合は日本語）
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPANESE.toString());
        //認識する候補数の指定
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);//かえた
        //音声認識時に表示する案内を設定
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "話してください");
        //音声認識を開始
        startActivityForResult(intent, REQUEST_CODE);

        timer = new Timer(false);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (flag == false && _myGlobals.onsei == false) {
                    Intent intent = new Intent(VoiceActivity.this, AutomaticVoice.class);
                    startActivity(intent);
                }else{
                    Intent intent2 = new Intent(VoiceActivity.this, Home.class);
                    startActivity(intent2);
                }
                timer.cancel();
            }
        };
        timer.schedule(task, 8000);

        long downTime = SystemClock.uptimeMillis();//タッチしなくても反応するようにしたい
        long eventTime = SystemClock.uptimeMillis() + 100;
        MotionEvent event = MotionEvent.obtain(
                downTime, eventTime, MotionEvent.ACTION_DOWN, 363, 722, 0);
        this.onTouchEvent(event);
    }

    //@Override
    public boolean onTouchEvent(MotionEvent event) {

        float posX = event.getX();
        float posY = event.getY();
        return false;
    }
       /* });
    }*/

    //音声認識が終わると自動で呼び出されるメソッド
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView exampleView = (TextView) findViewById(R.id.textView2);//テキスト非表示
        exampleView.setVisibility(View.GONE);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            //data から音声認識の結果を取り出す（リスト形式で）
            ArrayList<String> kekka = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //認識結果が一つ以上ある場合はテキストビューに結果を表示する
            if (kekka.size() > 0) {
                //一番最初にある認識結果を表示する
                textView2.setText(kekka.get(0));
                flag = true;
            } else {
                //何らかの原因で音声認識に失敗した場合はエラーメッセージを表示
                textView2.setText("音声の認識に失敗しました…");
            }

            TextView from = (TextView) findViewById(R.id.textView2);//ただいまの文字が認識できたら
            String setText = "";
            setText = from.getText().toString();
            if (setText.equals("ただいま")) {
                //変更する
                Intent intent = new Intent(VoiceActivity.this, okaeri.class);
                startActivity(intent);

                //おかえりの音声

            }
        }
    }
}
//選択した情報を保持したい
//マイグローバルに格納
//マイグローバルから名前を取得