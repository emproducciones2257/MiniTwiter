package com.emproducciones.minitwiter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.*;
import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.data.MiniTweeterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class BootomModalTweetFragment extends BottomSheetDialogFragment {

    private MiniTweeterViewModel MiniTweeterViewModel;
    private int idTweetAEliminar;

    public static BootomModalTweetFragment newInstance(int idTweet) {
        BootomModalTweetFragment fragment = new BootomModalTweetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constantes.ARG_TWEET_DELETE,idTweet);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(getArguments()!= null) {
           idTweetAEliminar = getArguments().getInt(Constantes.ARG_TWEET_DELETE);
       }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bootom_modal_tweet_fragment, container, false);

        final NavigationView fav = view.findViewById(R.id.navigationViewBootonTweet);
        fav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuItem = item.getItemId();

                if(menuItem == R.id.action_delete_tweet){
                    MiniTweeterViewModel.deleteTweet(idTweetAEliminar);
                    getDialog().dismiss();
                    return true;
                }
                return false;
            }
        });

        return view;
    }
}
