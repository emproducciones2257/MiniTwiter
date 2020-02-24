package com.emproducciones.minitwiter.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.emproducciones.minitwiter.Common.*;
import com.emproducciones.minitwiter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

public class DashboardActivity extends AppCompatActivity {

    FloatingActionButton fab;
    ImageView imgToolbarPhoto;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment f = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = TweetListFragment.newInstance(Constantes.TWEET_LIST_ALL);
                    break;
                case R.id.navigation_twit_like:
                    f = TweetListFragment.newInstance(Constantes.TWEET_LIST_FAV);
                    break;
                case R.id.navigation_profile:
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
        BottomNavigationView navView = findViewById(R.id.nav_view);

        fab = findViewById(R.id.fab);
        imgToolbarPhoto = findViewById(R.id.imgToolbarPhoto);

        getSupportActionBar().hide();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_twit_like, R.id.navigation_profile)
                .build();

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
