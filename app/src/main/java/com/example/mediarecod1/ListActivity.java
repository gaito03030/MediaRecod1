package com.example.mediarecod1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.util.SparseBooleanArray;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class ListActivity extends AppCompatActivity {

    private List<String> songList = new ArrayList<String>();
    private ListView lv;
    private File[] files;

    int posnum = 0;

    MyGlobals _myGlobals;
    MediaPlayer mediaPlayer;
    String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        _myGlobals = (MyGlobals) this.getApplication();
        mediaPlayer = new MediaPlayer();


        String sdPath = getFilesDir().getPath();

        Log.d("path", sdPath);
        files = new File(sdPath).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                //このifとfor文に入ってるか確認するLog
                Log.d("file", "ok");
                if (files[i].isFile() && files[i].getName().endsWith(".mp3")) {
                    songList.add(files[i].getName());
                }
            }
            lv = (ListView) findViewById(R.id.songlist);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, songList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for(int i = 0; i < lv.getChildCount(); i++){
                        lv.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }

                    view.setBackgroundColor(Color.parseColor("#dcdcdc"));
                    ListView listView = (ListView) parent;
                    String item = (String) listView.getItemAtPosition(position);
                    posnum = position;
                }
            });
        } else {
            //elseに入ったのを確認するLog
            Log.d("file", "null");
        }


        Button btn_ok = (Button)findViewById(R.id.select_button);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView)findViewById(R.id.songlist);
                _myGlobals.select = (String)listView.getItemAtPosition(posnum);
                Toast ts = Toast.makeText(v.getContext(),"選択されました",Toast.LENGTH_SHORT);
                ts.setGravity(Gravity.CENTER,-80,0);
                ts.show();
            }
        });

        Button delete_button = (Button)findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView)findViewById(R.id.songlist);
                _myGlobals.select = (String)listView.getItemAtPosition(posnum);
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) listView.getAdapter();  //リストのAdapter取得
                String item = adapter.getItem(posnum);
                String s = files[posnum].getName();
                deleteFile(item);
                adapter.remove(item);
                //更新の処理
                adapter.notifyDataSetChanged();     //Adapterに変更を通知
            }
        });

        Button play_button = (Button) findViewById(R.id.play_button);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView)findViewById(R.id.songlist);
                _myGlobals.select = (String)listView.getItemAtPosition(posnum);

                filePath =getFilesDir().getPath() + "/" + _myGlobals.select;
                try {
                    mediaPlayer.setDataSource(filePath);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });




        //前の画面に戻る
        findViewById(R.id.buttonBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });
    }
}
