package com.wjy.project.mytbs;

import android.text.TextUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wjy on 2020/2/11.
 */

public class LoadFileModel {


    public static void loadFile(String url, Callback<ResponseBody> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoadFileApi mLoadFileApi = retrofit.create(LoadFileApi.class);
        if (!TextUtils.isEmpty(url)) {
            Call<ResponseBody> call = mLoadFileApi.loadPdfFile(url);
            call.enqueue(callback);
        }

    }
}
