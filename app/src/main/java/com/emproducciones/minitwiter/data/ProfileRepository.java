package com.emproducciones.minitwiter.data;

import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.emproducciones.minitwiter.Common.MyApp;
import com.emproducciones.minitwiter.Retrofit.AuthTwitterClient;
import com.emproducciones.minitwiter.Retrofit.AuthTwitterService;
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestUserProfile;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUserProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    MutableLiveData<ResponseUserProfile> userProfile;



    ProfileRepository(){
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        userProfile = getProfile();
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
}
