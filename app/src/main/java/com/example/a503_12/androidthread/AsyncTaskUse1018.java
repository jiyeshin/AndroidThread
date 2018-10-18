package com.example.a503_12.androidthread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

class  AsyncTaskEx extends AsyncTask<Integer, Integer, Integer>{

    ProgressBar progressBar;
    TextView textView;
    int value;

    //생성자 - AsyncTask1018 클래스에서 데이터를 넘겨받기 위해 생성
    public AsyncTaskEx(ProgressBar progressBar, TextView textView, int value){
        this.progressBar = progressBar;
        this.textView = textView;
        this.value = value;
    }

    //1.인스턴스가 생성되면 가장 먼저 호출되는 메소드
    //메인 스레드에서 수행됨. 즉, 화면을 갱식하는 코드를 작성해도 됨.
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //ui초기화
        value = 0;
        progressBar.setProgress(value);
    }

    //2.비동기적인 작업을 처리하는 메소드.
    //매개변수는 클래스를 생성할 때 적용한 제너릭의 두번째 자료형과 일치해야 함. 여기서는 Integer
    //리턴 타입은 세번째 자료형과 일치해야 함. 여기서는 Integer
    //메인 스레드에서 동작하지 않음.
    //ui갱신하는 코드는 작성할 수 없음.
    @Override
    protected Integer doInBackground(Integer... integers) {
        while(isCancelled()==false){
            value = value +1;
            if(value >=100){
                break;
            }else {
                //onProgressUpdate 호출
                publishProgress(value);
            }
            try{
                Thread.sleep(100);
            }catch (Exception e){
                Log.e("문제 발생", e.getMessage());
            }
        }
        return value;
    }

    //3.doInBackground에서 publishProgress를 호출하면 자동으로 호출되는 메소드.
    //메인스레드에서 수행
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(value);
        textView.setText(String.format("%d 진행", value));
    }

    //3.인스턴스가 cancelled를 호출했을 때 호출되는 메소드
    //메인 스레드에서 실행
    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
        textView.setText("텍스트 중지");
    }

    //3.doInBackground가 작업을 종료했을 때 호출되는 메소드
    //메인 스레드에서 실행
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        value = 0;
        progressBar.setProgress(value);
        textView.setText("스레드 종료");
    }
}


public class AsyncTaskUse1018 extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    //프로그래스바의 값을 표시할 정수 변수 선언
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_use1018);

        Button btnStart = (Button)findViewById(R.id.btnStart);
        Button btnStop = (Button)findViewById(R.id.btnStop);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView);

        View.OnClickListener clickListener = new View.OnClickListener(){
            AsyncTaskEx task = null;
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnStart:
                        task = new AsyncTaskEx(progressBar,textView,value);
                        task.execute(100);
                        break;

                    case R.id.btnStop:
                        task.cancel(true);
                        break;
                }
            }
        };
        btnStart.setOnClickListener(clickListener);
        btnStop.setOnClickListener(clickListener);
    }
}
