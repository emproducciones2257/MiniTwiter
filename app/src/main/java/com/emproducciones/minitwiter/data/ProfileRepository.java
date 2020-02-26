package com.emproducciones.minitwiter.data;

import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;

import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.Common.MyApp;
import com.emproducciones.minitwiter.Common.SharedPreferencesManager;
import com.emproducciones.minitwiter.Retrofit.AuthTwitterClient;
import com.emproducciones.minitwiter.Retrofit.AuthTwitterService;
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestUserProfile;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUploadFoto;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUserProfile;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    MutableLiveData<ResponseUserProfile> userProfile;
    MutableLiveData<String> photoProfile;

    ProfileRepository(){
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        userProfile = getProfile();

        if(photoProfile==null){
            photoProfile= new MutableLiveData<>();
        }
    }

    public MutableLiveData<ResponseUserProfile> getProfile(){

        if(userProfile==null){
            userProfile= new MutableLiveData<>();

        }

        Call<ResponseUserProfile> call = authTwitterService.getProfileUser();

        call.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                if (response.isSuccessful()){
                    userProfile.setValue(response.body());
                }else Toast.makeText(MyApp.getContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });

        return userProfile;
    }

    public void updateUser(RequestUserProfile requestUserProfile){
        Call<ResponseUserProfile> call = authTwitterService.updateUser(requestUserProfile);

        call.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                if (response.isSuccessful()){
                    userProfile.setValue(response.body());
                }else Toast.makeText(MyApp.getContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadPhoto(String urlPhoto){
        File file = new File(urlPhoto);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);

        Call<ResponseUploadFoto> call = authTwitterService.uploadFoto(requestBody);

        call.enqueue(new Callback<ResponseUploadFoto>() {
            @Override
            public void onResponse(Call<ResponseUploadFoto> call, Response<ResponseUploadFoto> response) {
                if (response.isSuccessful()){
                    SharedPreferencesManager.setSomeStringValues(Constantes.PREF_PHOTO,response.body().getFilename());
                    photoProfile.setValue(response.body().getFilename());
                }else Toast.makeText(MyApp.getContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUploadFoto> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
