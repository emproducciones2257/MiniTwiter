package com.emproducciones.minitwiter.data;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.emproducciones.minitwiter.UI.tweets.BootomModalTweetFragment;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;
import java.util.List;

public class MiniTweeterViewModel extends AndroidViewModel {

    private MiniTwiterRepository miniTwiterRepository;
    private LiveData<List<Tweet>> listTweet;
    private LiveData<List<Tweet>> favTweet;


    public MiniTweeterViewModel(@NonNull Application application) {
        super(application);

        miniTwiterRepository = new MiniTwiterRepository();
        listTweet=miniTwiterRepository.getAllTweet();
    }

    public LiveData<List<Tweet>> getListTweet(){ return listTweet;}

    public void openDialogMenu(Context ctx, int idTeet){
        BootomModalTweetFragment bootomModalTweetFragment =  BootomModalTweetFragment.newInstance(idTeet);
        bootomModalTweetFragment.show(((AppCompatActivity)ctx).getSupportFragmentManager(),"bootomModalTweetFragment");
    }

    public LiveData<List<Tweet>> getFavTweet(){
           favTweet = miniTwiterRepository.getFavTweet();
        return favTweet;
    }

    public LiveData<List<Tweet>> getNewTweet(){

        return miniTwiterRepository.getAllTweet();
    }

    public LiveData<List<Tweet>> getNewFavTweet(){
        getNewTweet();
        return getFavTweet();
    }

    public void insertarTweet(String mensaje){
        miniTwiterRepository.createTweet(mensaje);
    }

    public void deleteTweet(int idTweet){
        miniTwiterRepository.deleteTweet(idTweet);
    }

    public void likeTweet(int idTweet){
        miniTwiterRepository.likeTweet(idTweet);
    }
}
