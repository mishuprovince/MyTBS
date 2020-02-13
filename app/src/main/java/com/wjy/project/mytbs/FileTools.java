package com.wjy.project.mytbs;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by wjy on 2020/2/11
 * 文件工具类
 */
public class FileTools {

    public static String pathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TbsReaderTemp/";

    /***
     * 获取缓存目录
     * @return
     */
    public static File getCacheDir() {
        return new File(pathName);
    }

    /***
     * 绝对路径获取缓存文件
     * @param url
     * @return
     */
    public static File getCacheFile(String url) {
        File cacheFile = new File(pathName + getFileName(url));
        Log.e("tag", "缓存文件 = " + cacheFile.toString());
        return cacheFile;
    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     * @param url
     * @return
     */
    private static String getFileName(String url) {
        String fileName = Md5Tool.hashKey(url) + "." + getFileType(url);
        Log.e("tag","fileName="+fileName);
        return fileName;
    }

    /***
     * 获取文件类型
     * @param paramString
     * @return
     */
    private static String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            Log.e("tag", "paramString---->null");
            return str;
        }
        Log.e("tag","paramString:"+paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.e("tag","i <= -1");
            return str;
        }

        str = paramString.substring(i + 1);
        Log.e("tag","paramString.substring(i + 1)------>"+str);
        return str;
    }
}
