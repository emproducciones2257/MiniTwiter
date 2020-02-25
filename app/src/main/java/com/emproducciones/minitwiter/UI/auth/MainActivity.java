package com.emproducciones.minitwiter.UI.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.Common.SharedPreferencesManager;
import com.emproducciones.minitwiter.R;
import com.emproducciones.minitwiter.Retrofit.MiniTwitterClient;
import com.emproducciones.minitwiter.Retrofit.MiniTwitterService;
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestLogin;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseAuth;
import com.emproducciones.minitwiter.UI.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnIniSesio;
    TextView txtGoSingUp;
    EditText edtEmail,edtContrase;
    MiniTwitterService miniTwitterService;
    MiniTwitterClient miniTwitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();//oulta la action bar

        retrofitIni();
        //inicio instancias
        finVie();
        actions();
    }

    private void retrofitIni() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void actions() {
        btnIniSesio.setOnClickListener(this);
        txtGoSingUp.setOnClickListener(this);
    }

    private void finVie() {
        btnIniSesio = findViewById(R.id.btnLogin);
        txtGoSingUp = findViewById(R.id.txtSingIn);
        edtEmail = findViewById(R.id.edtEmail);
        edtContrase = findViewById(R.id.edtContrase);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btnLogin:
                goToLogin();
                break;

            case R.id.txtSingIn:
                goToSignUp();
                break;
        }
    }

    private void goToLogin() {
        String email = edtEmail.getText().toString();
        String contra = edtContrase.getText().toString();

        if(email.isEmpty()){
            edtEmail.setError("Email es requerido");
        }else if (contra.isEmpty()){
            edtContrase.setError("Contraseña es requerida");
        }else {
            RequestLogin requestLogin = new RequestLogin(email,contra);
            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin).clone();
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Inisio de sesión correcto", Toast.LENGTH_SHORT).show();

                        //gestion SharedPreferences
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_TOKEN,response.body().getToken());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_USER,response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_EMAIL,response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_CREATE,response.body().getCreated());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_PHOTO,response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeBooleanValues(Constantes.PREF_ACTIVE,response.body().getActive());

                        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(i);
                        //destruyo actividad para no volver al login
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this, "Algo fue mal, verifique sus datos de acceso", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problemas de conexión, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void goToSignUp() {
        Intent i = new Intent(MainActivity.this, SingUpActivity.class);
        startActivity(i);
        finish();
    }
}
