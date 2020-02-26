package com.emproducciones.minitwiter.UI.tweets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.emproducciones.minitwiter.Common.*;
import com.emproducciones.minitwiter.R;
import com.emproducciones.minitwiter.data.MiniTweeterViewModel;

public class NuevoTwitDialogFragment extends DialogFragment implements View.OnClickListener {

    ImageView imgCerrar,imgAvatar;
    Button btnTweetear;
    EditText extMensajeTweet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nuevo_twit_dialog, container, false);

        imgCerrar = view.findViewById(R.id.imgCerrar);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        btnTweetear = view.findViewById(R.id.btnTweetear);
        extMensajeTweet = view.findViewById(R.id.extMensajeTweet);

        // acciones

        btnTweetear.setOnClickListener(this);
        imgCerrar.setOnClickListener(this);

        //setear imagen de usuario
        String urlPhoto = SharedPreferencesManager.getSomeStringValues(Constantes.PREF_PHOTO);
        if (!urlPhoto.isEmpty()){

            Glide.with(getContext()).
                    load(Constantes.URL_IMAGENES).
                    dontAnimate().
                    diskCacheStrategy(DiskCacheStrategy.NONE).
                    skipMemoryCache(true).
                    centerCrop().
                    into(imgAvatar);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String mensaje = extMensajeTweet.getText().toString();

        if (id==R.id.btnTweetear){

            if (mensaje.isEmpty()){
                Toast.makeText(getActivity(), "Debe ingresar un mensaje", Toast.LENGTH_SHORT).show();
            }else {

                MiniTweeterViewModel miniTweeterViewModel = ViewModelProviders.
                        of(getActivity()).
                        get(MiniTweeterViewModel.class);
                miniTweeterViewModel.insertarTweet(mensaje);
                getDialog().dismiss();
            }

        }else if (id==R.id.imgCerrar){
            if(!mensaje.isEmpty()){
                showDialogConfirm();
            }else getDialog().dismiss();
        }
    }

    private void showDialogConfirm() {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Desea realmente eliminar el tweet, el mensaje se borrara")
                .setTitle("Cancelar Tweet");

        // Add the buttons
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
                getDialog().dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
