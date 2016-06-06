package com.johnson.httpbesttest;

/**
 * Created by Administrator on 2016/6/6.
 */
//使用java的回调机制，定义一个接口
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
