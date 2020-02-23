package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Common.Constantes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient {

    private static AuthTwitterClient instance = null;
    private static AuthTwitterService minitwitterService;
    private Retrofit retrofit;

    public AuthTwitterClient(){

        //incluir en cabecera de peticion el token que autoriza al usuario
        OkHttpClient.Builder okhttpclientebuilde = new OkHttpClient.Builder();
        okhttpclientebuilde.addInterceptor(new AutInterceptor());
        OkHttpClient client = okhttpclientebuilde.build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)//direccion base de las conexiones
                .addConverterFactory(GsonConverterFactory.create())//conversor json
                .client(client) //interceptor
                .build();

        minitwitterService = retrofit.create(AuthTwitterService.class);
    }

    //Patron Singleton
    public static AuthTwitterClient getInstance(){
        if(instance==null){
            instance = new AuthTwitterClient();
        }
        return instance;
    }

    public static AuthTwitterService getAuthTwitterService(){
        return minitwitterService;
    }


}
