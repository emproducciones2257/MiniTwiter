package com.emproducciones.minitwiter.data;

import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.Common.MyApp;
import com.emproducciones.minitwiter.Common.SharedPreferencesManager;
import com.emproducciones.minitwiter.Retrofit.AuthTwitterClient;
import com.emproducciones.minitwiter.Retrofit.AuthTwitterService;
import com.emproducciones.minitwiter.Retrofit.Response.Like;
import com.emproducciones.minitwiter.Retrofit.Response.RequestCreateTweet;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniTwiterRepository {

    AuthTwitterClient authTwitterClient;
    AuthTwitterService authTwitterService;
    MutableLiveData<List<Tweet>> allTweet;
    MutableLiveData<List<Tweet>> favTweet;
    String user;


    MiniTwiterRepository(){
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = authTwitterClient.getAuthTwitterService();
        allTweet = getAllTweet();
        user = SharedPreferencesManager.getSomeStringValues(Constantes.PREF_USER);
    }

    public MutableLiveData<List<Tweet>> getAllTweet(){

        if(allTweet==null){
            allTweet= new MutableLiveData<>();

        }

        Call<List<Tweet>> listCall = authTwitterService.getAllTweet();

        listCall.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()){
                    allTweet.setValue(response.body());
                }else {
                    Toast.makeText(MyApp.getContext(), "Algo salió mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Fallo conexión", Toast.LENGTH_SHORT).show();
            }
        });
        return allTweet;
    }

    public void createTweet(String mensajeTweet){

        RequestCreateTweet requestCreateTweet = new RequestCreateTweet(mensajeTweet);

        Call<Tweet> call = authTwitterService.crearTweet(requestCreateTweet);

        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if(response.isSuccessful()){
                   List<Tweet> listClonada = new ArrayList<>();
                   listClonada.add(response.body());

                   for (int i = 0; i<allTweet.getValue().size();i++){
                       listClonada.add(new Tweet(allTweet.getValue().get(i)));
                   }

                   allTweet.setValue(listClonada);
                }else {
                    Toast.makeText(MyApp.getContext(), "Algo Salio Mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion, intentelo nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void likeTweet(int idTweet){

        Call<Tweet> call = authTwitterService.likeTweet(idTweet);

        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if(response.isSuccessful()){
                    List<Tweet> listClonada = new ArrayList<>();

                    for (int i = 0; i<allTweet.getValue().size();i++){
                        if(allTweet.getValue().get(i).getId()==idTweet){
                            /*si se encuntra en la lista original el elmento sobre el que hemos
                            echo like, introducimos el elemento que nos llego del servidor
                            con el like
                             */
                            listClonada.add(response.body());

                        }else {
                            listClonada.add(new Tweet(allTweet.getValue().get(i)));
                        }
                    }
                    allTweet.setValue(listClonada);

                    getFavTweet();
                }else {
                    Toast.makeText(MyApp.getContext(), "Algo Salio Mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion, intentelo nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<List<Tweet>> getFavTweet(){
        if(favTweet==null){
            favTweet=new MutableLiveData<>();
        }

        List<Tweet> newFavList = new ArrayList<>();

        Iterator ir = allTweet.getValue().iterator();
        while (ir.hasNext()){
            Tweet current =(Tweet) ir.next();
            Iterator itLike = current.getLikes().iterator();
            boolean enc = false;
            while (itLike.hasNext()&& !enc){
                Like like = (Like) itLike.next();
                if(like.getUsername().equals(user)){
                    enc= true;
                    newFavList.add(current);
                }
            }
        }

        favTweet.setValue(newFavList);

        return favTweet;
    }

}
