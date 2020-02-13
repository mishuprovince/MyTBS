package com.wjy.project.mytbs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wjy on 2020/2/11
 * 文件打开阅读页面
 */
public class FileDisplayActivity extends Activity {

    SuperFileView mSuperFileView;
    String filePathUrl;
    private TextView tv_progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filedisplay);
        filePathUrl = getIntent().getStringExtra("filePath");
        Log.e("tag","filePathUrl="+filePathUrl);
        init();
    }

    private void init(){
        tv_progress = findViewById(R.id.tv_progress);
        mSuperFileView = findViewById(R.id.mSuperFileView);
        mSuperFileView.setOnGetFilePathListener(new SuperFileView.OnGetFilePathListener() {
            @Override
            public void onGetFilePath(SuperFileView mSuperFileView) {
                openFile(filePathUrl,mSuperFileView);//获取文件路径并打开文件
            }
        });
        mSuperFileView.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tag","FileDisplayActivity-->onDestroy");
        if (mSuperFileView != null) {
            mSuperFileView.onStopDisplay();
        }
    }

    /**
     * 打开文件文件
     * @param filePathUrl
     * @param mSuperFileView
     */
    private void openFile(final String filePathUrl,final SuperFileView mSuperFileView){
        //从缓存获取文件
        File cacheFile = FileTools.getCacheFile(filePathUrl);
        if (cacheFile.exists()) {//如果文件存在
            if (cacheFile.length() <= 0) {
                Log.e("tag", "删除空文件！！");
                cacheFile.delete();
                return;
            }
            Log.e("tag","如果文件存在"+cacheFile.exists());
            mSuperFileView.displayFile(cacheFile);//直接打开文件
        }else {
            tv_progress.setVisibility(View.VISIBLE);
            //如果文件不存在，则在网络先下载然后再打开
            requestDownLoadFile();
        }
    }

    /**
     * 从网络下载文件
     */
    private void requestDownLoadFile(){
        //网络下载
        LoadFileModel.loadFile(filePathUrl, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("tag", "下载文件-->onResponse");
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                try {
                    ResponseBody responseBody = response.body();
                    is = responseBody.byteStream();
                    long total = responseBody.contentLength();

                    File file1 = FileTools.getCacheDir();
                    if (!file1.exists()) {
                        file1.mkdirs();
                        Log.e("tag", "创建缓存目录： " + file1.toString());
                    }

                    File fileN = FileTools.getCacheFile(filePathUrl);
                    Log.e("tag", "创建缓存文件： " + fileN.toString());
                    if (!fileN.exists()) {
                        boolean mkdir = fileN.createNewFile();
                    }
                    fos = new FileOutputStream(fileN);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        Log.e("tag", "写入缓存文件" + fileN.getName() + "进度: " + progress);
                    }
                    fos.flush();
                    Log.e("tag", "文件下载成功,准备展示文件。");
                    tv_progress.setVisibility(View.GONE);
                    mSuperFileView.displayFile(fileN);
                } catch (Exception e) {
                    Log.e("tag", "文件下载异常 = " + e.toString());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("tag", "文件下载失败");
                File file = FileTools.getCacheFile(filePathUrl);
                if (!file.exists()) {
                    Log.e("tag", "删除下载失败文件");
                    file.delete();
                    requestDownLoadFile();//重新下载
                }
            }
        });
    }
}
