package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestLogin;
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestSingUp;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService {

    @POST("auth/login")
    Call<ResponseAuth>doLogin(@Body RequestLogin requestLogin);

    @POST("auth/signup")
    Call<ResponseAuth>doSignUp(@Body RequestSingUp requestSingUp);
}
