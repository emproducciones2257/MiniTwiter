package com.emproducciones.minitwiter.UI;

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
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestSingUp;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSingUp;
    TextView txtGoLogin;
    EditText txtUserName,edtEmail,edtContrase;
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        getSupportActionBar().hide();

        retrofitInit();
        //inicio instancias
        findViews();
        actions();
    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void actions() {
        txtGoLogin.setOnClickListener(this);
        btnSingUp.setOnClickListener(this);
    }

    private void findViews() {
        txtGoLogin = findViewById(R.id.txtGoLogin);
        btnSingUp = findViewById(R.id.btnSingUp);
        txtUserName = findViewById(R.id.txtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtContrase = findViewById(R.id.edtContrase);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btnSingUp:
                doSingIn();
                break;

            case R.id.txtGoLogin:
                goToLogin();
                break;
        }
    }

    private void doSingIn() {
        final String nombre = txtUserName.getText().toString();
        String email = edtEmail.getText().toString();
        String pasw = edtContrase.getText().toString();
        String code = "UDEMYANDROID";

        if(nombre.isEmpty()){
            txtUserName.setError("Ingresar Nombre");
        }else if(email.isEmpty()){
            edtEmail.setError("Igresar email");
        }else if(pasw.isEmpty() || pasw.length()<4){
            edtContrase.setError("Ingresar contraseña con 4 digitos como minimo");
        }else {

            RequestSingUp requestSingUp = new RequestSingUp(nombre,email,pasw,code);

            Call<ResponseAuth> call = miniTwitterService.doSignUp(requestSingUp);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){

                        //gestion SharedPreferences
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_TOKEN,response.body().getToken());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_USER,response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_EMAIL,response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_CREATE,response.body().getCreated());
                        SharedPreferencesManager.setSomeStringValues(Constantes.PREF_PHOTO,response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeBooleanValues(Constantes.PREF_ACTIVE,response.body().getActive());


                        Intent i = new Intent(SingUpActivity.this,DashboardActivity.class);
                        startActivity(i);
                        Toast.makeText(SingUpActivity.this, nombre + " registrado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }else Toast.makeText(SingUpActivity.this, "Algo salio mal en el registro", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SingUpActivity.this, "Verificar conexión a Internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void goToLogin() {
        Intent i = new Intent(SingUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
