package com.wjy.project.mytbs;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by wjy on 2020/2/11.
 */

public interface LoadFileApi {

    @GET
    Call<ResponseBody> loadPdfFile(@Url String fileUrl);

}
