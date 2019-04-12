package com.example.admin.myapplication.databean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviceService {
    @GET("bool/{id}")
    Call<ResponseBody> getBook(@Path("id") int id);
}
