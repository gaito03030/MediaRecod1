package com.example.mediarecod1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Bouhan extends AppCompatActivity {

    MyGlobals myGlobals ;
    boolean onsei = false;//偽

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.automaticvoice);

        myGlobals = (MyGlobals) this.getApplication();

        //前の画面に戻る
        findViewById(R.id.buttonBack).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        myGlobals.onsei =onsei;

        CompoundButton toggle = (CompoundButton) findViewById(R.id.toggle1);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("MainActivity", "ON");
                    Toast.makeText(buttonView.getContext(),"防犯機能がONになりました" ,Toast.LENGTH_SHORT).show();

                    onsei = false;

                } else {
                    Log.d("MainActivity", "OFF");
                    Toast.makeText(buttonView.getContext(),"防犯機能がOFFになりました",Toast.LENGTH_SHORT).show();
                    onsei = true;//真
                }


                myGlobals.onsei = onsei;
            }
        });
    }
}