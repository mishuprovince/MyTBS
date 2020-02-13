package com.wjy.project.mytbs;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by wjy
 * on 2020/2/11.
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //增加这句话
        QbSdk.initX5Environment(this,null);
    }


}
