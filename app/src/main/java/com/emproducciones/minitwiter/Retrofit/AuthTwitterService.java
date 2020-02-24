package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Retrofit.Response.RequestCreateTweet;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweet>> getAllTweet();

    @POST("tweets/create")
    Call<Tweet> crearTweet(@Body RequestCreateTweet requestCreateTweet);

    @POST("tweets/like/{idTweet}")
    Call<Tweet> likeTweet(@Path("idTweet") int idTweet);
}
