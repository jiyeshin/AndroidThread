package com.example.a503_12.androidthread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadBasic1017 extends AppCompatActivity {

    TextView textView;
    Button btn1, btn2;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic1017);

        textView = (TextView) findViewById(R.id.txtView);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                class T extends Thread{
                    public void run(){
                        int j= 0;
                        while(j<10){
                            try{
                                Thread.sleep(1000);
                                textView.setText(j+"");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            j=j+1;
                        }
                    }
                }

                new T().start();


            }
        });
/*
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                a=a+1;
                Toast.makeText(ThreadBasic1017.this, a+"", Toast.LENGTH_SHORT).show();

            }
        });
        */
    }
}
