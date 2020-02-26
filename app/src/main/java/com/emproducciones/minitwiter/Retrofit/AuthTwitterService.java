package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestUserProfile;
import com.emproducciones.minitwiter.Retrofit.Response.DeleteTweet;
import com.emproducciones.minitwiter.Retrofit.Response.RequestCreateTweet;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUploadFoto;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUserProfile;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AuthTwitterService {

    //Tweet
    @GET("tweets/all")
    Call<List<Tweet>> getAllTweet();

    @POST("tweets/create")
    Call<Tweet> crearTweet(@Body RequestCreateTweet requestCreateTweet);

    @POST("tweets/like/{idTweet}")
    Call<Tweet> likeTweet(@Path("idTweet") int idTweet);

    @DELETE("tweets/{idTweetDelete}")
    Call<DeleteTweet> deleteTweet(@Path("idTweetDelete") int idTweetDelete);

    //User

    @GET("users/profile")
    Call<ResponseUserProfile> getProfileUser();

    @PUT("users/profile")
    Call<ResponseUserProfile> updateUser(@Body RequestUserProfile requestUserProfile);

    @Multipart
    @POST("users/uploadprofilephoto")
    Call<ResponseUploadFoto> uploadFoto(@Part ("file\"; filename=\"photo.jpg\" ")RequestBody file);
}
