package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Retrofit.Response.RequestCreateTweet;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweet>> getAllTweet();

    @POST("tweets/create")
    Call<Tweet> crearTweet(@Body RequestCreateTweet requestCreateTweet);
}
