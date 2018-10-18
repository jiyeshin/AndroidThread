package com.example.a503_12.androidthread;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class TCP1018 extends AppCompatActivity {

    EditText txtIP, txtPort, txtMsg;
    TextView txtResult;

    //액티비티 클래스에 전송되어 온 메시지를 토스트로 출력할 핸들러 생성
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(TCP1018.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    //TCP 서버에게 문자열을 전송하고 받아올 스레드 클래스를 생성
    class ThreadEx extends Thread{
        @Override
        public void run() {
            String ipAddr = txtIP.getText().toString();
            String portNumber = txtPort.getText().toString();
            String str = txtMsg.getText().toString();

            try {
                //소켓 생성
                Socket socekt = new Socket(ipAddr, Integer.parseInt(portNumber));

                //문자열을 전송하기 위한 스트림을 생성
                PrintWriter pw = new PrintWriter(socekt.getOutputStream());
                pw.println(str);
                pw.flush();


                //문자열을 입력받기 위한 스트림 생성
                BufferedReader br = new BufferedReader(new InputStreamReader(socekt.getInputStream()));
                Message message = new Message();
                message.obj = br.readLine();
                handler.sendMessage(message);

                pw.close();
                br.close();
                socekt.close();

            }catch (Exception e){
                Log.e("소켓소켓", e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp1018);

        txtIP = (EditText) findViewById(R.id.txtIP);
        txtPort = (EditText)findViewById(R.id.txtPort);
        txtMsg = (EditText)findViewById(R.id.txtMsg);
        txtResult = (TextView)findViewById(R.id.txtResult);


        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadEx th = new ThreadEx();
                th.start();
                txtMsg.setText("");
            }
        });

    }
}
