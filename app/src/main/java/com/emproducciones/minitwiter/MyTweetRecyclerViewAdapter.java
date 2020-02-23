package com.emproducciones.minitwiter;

import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.Common.SharedPreferencesManager;
import com.emproducciones.minitwiter.Retrofit.Response.Like;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;
import java.util.List;

public class MyTweetRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder> {

    private List<Tweet> mValues;
    Context ctx;
    String usuario;

    public MyTweetRecyclerViewAdapter(Context ctx, List<Tweet> items) {
        mValues = items;
        this.ctx=ctx;
        usuario = SharedPreferencesManager.getSomeStringValues(Constantes.PREF_USER);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues!=null){
            holder.mItem = mValues.get(position);
            holder.txtUserName.setText("@" + holder.mItem.getUser().getUsername());
            holder.txtMessage.setText(holder.mItem.getMensaje());
            holder.txtCountLikes.setText(holder.mItem.getLikes().size()+"");

            String dir = holder.mItem.getUser().getPhotoUrl();

                if(!dir.equals("")){
                    Glide.with(ctx)
                        .load(Constantes.URL_IMAGENES + dir)
                        .into(holder.imageViewAvatar);
                }

            Glide.with(ctx)
                    .load(R.drawable.ic_favorite_border_black_24dp)
                    .into(holder.imgLike);

            holder.txtCountLikes.setTextColor(ctx.getResources().getColor(android.R.color.black));
            holder.txtCountLikes.setTypeface(null, Typeface.BOLD);

                for(Like e : holder.mItem.getLikes()){
                    if(e.getUsername().equals(usuario)){
                        Glide.with(ctx)
                            .load(R.drawable.ic_favorite_pink_24dp)
                            .into(holder.imgLike);

                            holder.txtCountLikes.setTextColor(ctx.getResources().getColor(R.color.pink));
                            holder.txtCountLikes.setTypeface(null, Typeface.BOLD);

                             break;
                        }
                }
        }
    }

    public void sendData(List<Tweet> tweetList){
        this.mValues=tweetList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues!=null){
            return mValues.size();
        }else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewAvatar;
        public final ImageView imgLike;
        public final TextView txtUserName;
        public final TextView txtMessage;
        public final TextView txtCountLikes;
        public Tweet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
            imgLike = view.findViewById(R.id.imgLike);
            txtUserName = view.findViewById(R.id.txtUserName);
            txtMessage = view.findViewById(R.id.txtMessage);
            txtCountLikes = view.findViewById(R.id.txtCountLikes);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getUser()+ "'";
        }
    }
}
