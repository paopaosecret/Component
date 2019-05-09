package com.xbing.app.account.iml;

import com.xbing.app.account.entity.MovieObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("top250")
    Call<MovieObject> getTop250(@Query("start")int start, @Query("count")int Count);
}
