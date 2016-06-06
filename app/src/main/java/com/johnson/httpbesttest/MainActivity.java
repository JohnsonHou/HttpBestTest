package com.johnson.httpbesttest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String address="http://www.baidu.com";
    private Button btn;
    private TextView tv;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    tv.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        btn= (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
                    //onFinish()和onError()还是在子线程中，因此如果对UI进行操作需要用到异步机制
                    @Override
                    public void onFinish(String response) {
                        //根据返回内容执行具体的逻辑
                        Message message=new Message();
                        message.obj=response;
                        message.what=1;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {
                        //对异常情况进行处理
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}
