package com.emproducciones.minitwiter.UI.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.R;
import com.emproducciones.minitwiter.Retrofit.Request.Response.RequestUserProfile;
import com.emproducciones.minitwiter.Retrofit.Response.ResponseUserProfile;
import com.emproducciones.minitwiter.data.ProfileUserViewModel;

public class profileUserFragment extends Fragment {

    private ProfileUserViewModel profileUserViewModel;
    ImageView imgAvatarProfile;
    EditText txtUserNameProfile,txtEmailProfile,txtPaswordProfile,edtWebSite,edtInteres;
    Button btnGuardarProfile, btnCambiarPasword;
    Boolean loagingData = true;

    public static profileUserFragment newInstance() {
        return new profileUserFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileUserViewModel = ViewModelProviders.of(this).get(ProfileUserViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_user_fragment, container, false);

        imgAvatarProfile = view.findViewById(R.id.imgAvatarProfile);
        txtUserNameProfile = view.findViewById(R.id.txtUserNameProfile);
        txtEmailProfile = view.findViewById(R.id.txtEmailProfile);
        txtPaswordProfile = view.findViewById(R.id.txtPaswordProfile);
        btnGuardarProfile = view.findViewById(R.id.btnGuardarProfile);
        btnCambiarPasword = view.findViewById(R.id.btnCambiarPasword);
        edtWebSite = view.findViewById(R.id.edtWebSite);
        edtInteres = view.findViewById(R.id.edtInteres);

        // Eventos

        btnGuardarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUserNameProfile.getText().toString();
                String email = txtEmailProfile.getText().toString();
                String descripcion=edtInteres.getText().toString();
                String website=edtWebSite.getText().toString();
                String password=txtPaswordProfile.getText().toString();

                if(username.isEmpty()){
                    txtUserNameProfile.setError("El usuario es requerido");
                }else if(email.isEmpty()){
                    txtEmailProfile.setError("El usuario es requerido");
                }else if(password.isEmpty()){
                    txtPaswordProfile.setError("La contraseña es requerida");
                }else {
                    RequestUserProfile requestUserProfile = new RequestUserProfile( username,  email,  descripcion,  website,  password);
                    profileUserViewModel.updateProfile(requestUserProfile);
                    Toast.makeText(getContext(), "Enviando informacion al servidor", Toast.LENGTH_SHORT).show();
                    btnGuardarProfile.setEnabled(false);
                }
            }
        });

        btnCambiarPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgAvatarProfile.setOnClickListener(view1 -> {
            //Invocar la seleccion de la fotografia

        });

        //ViewModel
        profileUserViewModel.userProfileLiveData.observe(getActivity(), new Observer<ResponseUserProfile>() {
            @Override
            public void onChanged(ResponseUserProfile responseUserProfile) {
                loagingData=false;
                txtUserNameProfile.setText(responseUserProfile.getUsername());
                txtEmailProfile.setText(responseUserProfile.getEmail());
                edtWebSite.setText(responseUserProfile.getWebsite());
                edtInteres.setText(responseUserProfile.getDescripcion());

                if(!responseUserProfile.getPhotoUrl().isEmpty()){
                    Glide.with(getActivity()).
                            load(Constantes.URL_IMAGENES + responseUserProfile.
                                    getPhotoUrl()).
                            dontAnimate().
                            diskCacheStrategy(DiskCacheStrategy.NONE).
                            skipMemoryCache(true).
                            centerCrop().
                            into(imgAvatarProfile);
                }
                if (!loagingData){
                    btnGuardarProfile.setEnabled(true);
                    Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }



}
