package com.emproducciones.minitwiter.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.emproducciones.minitwiter.Common.*;
import com.emproducciones.minitwiter.R;
import com.emproducciones.minitwiter.UI.profile.profileUserFragment;
import com.emproducciones.minitwiter.UI.tweets.NuevoTwitDialogFragment;
import com.emproducciones.minitwiter.UI.tweets.TweetListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ImageView imgToolbarPhoto;

    private BottomNavigationView.OnNavigationItemSelectedListener appBarConfiguration
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment f = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = TweetListFragment.newInstance(Constantes.TWEET_LIST_ALL);
                    fab.show();
                    break;
                case R.id.navigation_twit_like:
                    f = TweetListFragment.newInstance(Constantes.TWEET_LIST_FAV);
                    fab.hide();
                    break;
                case R.id.navigation_profile:
                    f = new profileUserFragment();
                    fab.hide();
                    break;
            }

            if(f != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, f)
                        .commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navView = findViewById(R.id.navView);

        fab = findViewById(R.id.fab);
        imgToolbarPhoto = findViewById(R.id.imgToolbarPhoto);

        getSupportActionBar().hide();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navView);
        navigation.setOnNavigationItemSelectedListener(appBarConfiguration);


        getSupportFragmentManager().
                beginTransaction().
                add(R.id.fragmentContainer,TweetListFragment.newInstance(Constantes.TWEET_LIST_ALL)).
                commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NuevoTwitDialogFragment dialogFragment = new NuevoTwitDialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"dialogFragment");
            }
        });

        //setear imagen de usuario
        String urlPhoto = SharedPreferencesManager.getSomeStringValues(Constantes.PREF_PHOTO);
        if (!urlPhoto.isEmpty()){

            Glide.with(this).
                    load(Constantes.URL_IMAGENES).
                    into(imgToolbarPhoto);
        }
    }
}
