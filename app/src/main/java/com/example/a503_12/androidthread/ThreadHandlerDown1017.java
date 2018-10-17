package com.example.a503_12.androidthread;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThreadHandlerDown1017 extends AppCompatActivity {

    //진행율을 표시하기 위한 대화상자
    ProgressDialog progressDialog;
    //값을 나타낼 변수
    int value;

    Handler handler = new Handler(){
        public void handleMessage(Message message){
            value = value + 1;
            try{
                Thread.sleep(70);
                if(value<100){
                    progressDialog.setProgress(value);
                    handler.sendEmptyMessage(0);
                }else {
                    //다운 다 받으면 종료
                    progressDialog.dismiss();
                }
            }catch (Exception e){}
        }
    };

    public void download(){
        try{
            Thread.sleep(5000);
            Toast.makeText(ThreadHandlerDown1017.this, "다운로드 완료", Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler_down1017);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ThreadHandlerDown1017.this)
                        .setTitle("다운로드")
                        .setMessage("다운로드 하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                value =0;
                                progressDialog = new ProgressDialog(ThreadHandlerDown1017.this);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDialog.setTitle("다운로드");
                                progressDialog.setMessage("다운로드 중...");

                                //뒤로가기 버튼을 눌러도 대화상자를 닫을 수 없게 설정
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                handler.sendEmptyMessage(0);
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();
            }
        });
    }
}
