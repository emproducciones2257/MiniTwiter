package com.emproducciones.minitwiter.data;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emproducciones.minitwiter.Retrofit.Response.Tweet;

import java.util.List;

public class MiniTweeterViewModel extends AndroidViewModel {

    private MiniTwiterRepository miniTwiterRepository;
    private LiveData<List<Tweet>> listTweet;


    public MiniTweeterViewModel(@NonNull Application application) {
        super(application);

        miniTwiterRepository = new MiniTwiterRepository();
        listTweet=miniTwiterRepository.getAllTweet();
    }

    public LiveData<List<Tweet>> getListTweet(){ return listTweet;}

    public void insertarTweet(String mensaje){
        miniTwiterRepository.createTweet(mensaje);
    }
}
