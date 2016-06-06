package com.johnson.httpbesttest;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/6/6.
 */

//调取全局Context用
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
