package com.johnson.httpbesttest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/6/6.
 */
public class HttpUtil {
    public static void sendHttpRequest( final String address, final HttpCallbackListener httpCallbackListener){
        if(!isNetworkAvailable()){
            //调用全局context
            Toast.makeText(MyApplication.getContext(),"network is unavailable",Toast.LENGTH_SHORT).show();
            return;
        }
        //开启新线程，进行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    URL url=new URL(address);
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if (httpCallbackListener!=null){
                        //回调onFinish()方法
                        httpCallbackListener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if(httpCallbackListener!=null){
                        //回调onError()方法
                        httpCallbackListener.onError(e);
                    }
                } finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if((networkInfo==null)||(!networkInfo.isAvailable())){
            return false;
        }else{
            return true;
        }
    }
}
