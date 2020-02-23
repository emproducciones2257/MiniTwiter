package com.emproducciones.minitwiter.Retrofit;

import com.emproducciones.minitwiter.Common.Constantes;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {

    private static MiniTwitterClient instance = null;
    private static MiniTwitterService minitwitterService;
    private Retrofit retrofit;

    public MiniTwitterClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)//direccion base de las conexiones
                .addConverterFactory(GsonConverterFactory.create())//conversor json
                .build();

        minitwitterService = retrofit.create(MiniTwitterService.class);
    }

    //Patron Singleton
    public static MiniTwitterClient getInstance(){
        if(instance==null){
            instance = new MiniTwitterClient();
        }
        return instance;
    }

    public static MiniTwitterService getMiniTwitterService(){
        return minitwitterService;
    }


}
