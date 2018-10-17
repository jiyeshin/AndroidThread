package com.example.a503_12.androidthread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HandlerUse1017 extends AppCompatActivity {

    TextView textView;
    Handler handler = new Handler(){
        int i = 0;

        @Override
        public void handleMessage(Message message){
            textView.setText(i + "");
            i = i + 1;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_use1017);

        textView = (TextView)findViewById(R.id.textView);
        new Thread(){
            @Override
            public void run() {
                for(int i =0; i<10; i=i+1){
                    try{
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(0);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
