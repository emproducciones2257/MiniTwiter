package com.emproducciones.minitwiter.UI;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.*;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.emproducciones.minitwiter.Common.Constantes;
import com.emproducciones.minitwiter.MyTweetRecyclerViewAdapter;
import com.emproducciones.minitwiter.R;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;
import com.emproducciones.minitwiter.data.MiniTweeterViewModel;

import java.util.List;

public class TweetListFragment extends Fragment {


    private int tweetListType = 1;
    RecyclerView recyclerView;
    MyTweetRecyclerViewAdapter myTweetRecyclerViewAdapter;
    List<Tweet> TweetList;
    MiniTweeterViewModel miniTweeterViewModel;
    SwipeRefreshLayout swipeRefreshLayout;

    public TweetListFragment() {
    }

    public static TweetListFragment newInstance(int tweetListType) {
        TweetListFragment fragment = new TweetListFragment();
        Bundle args = new Bundle();
        args.putInt(Constantes.TWEET_LIST_TYPE, tweetListType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miniTweeterViewModel = ViewModelProviders.of(getActivity())
                .get(MiniTweeterViewModel.class);

        if (getArguments() != null) {
            tweetListType = getArguments().getInt(Constantes.TWEET_LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);

        // Set the adapter

            Context context = view.getContext();
            swipeRefreshLayout = view.findViewById(R.id.swiperefreslayout);
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAzul));
            recyclerView = (RecyclerView) view.findViewById(R.id.list);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);
                    if(tweetListType==Constantes.TWEET_LIST_ALL){
                        loadTweetData();
                    }else if(tweetListType==Constantes.TWEET_LIST_FAV){
                        loadNewDatFav();
                    }
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            myTweetRecyclerViewAdapter = new MyTweetRecyclerViewAdapter(
                    getActivity(),
                    TweetList);
            recyclerView.setAdapter(myTweetRecyclerViewAdapter);

            if(tweetListType==Constantes.TWEET_LIST_ALL){
                loadTweetData();
            }else if(tweetListType==Constantes.TWEET_LIST_FAV){
                loadFavTweetData();
            }

            loadTweetData();

        return view;
    }

    private void loadNewDatFav() {

        miniTweeterViewModel.getNewTweet().observe(getActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                TweetList = tweets;
                swipeRefreshLayout.setRefreshing(false);
                myTweetRecyclerViewAdapter.sendData(TweetList);
                miniTweeterViewModel.getNewFavTweet().removeObserver(this);
            }
        });
    }

    private void loadFavTweetData() {

        miniTweeterViewModel.getFavTweet().observe(getActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                TweetList = tweets;
                myTweetRecyclerViewAdapter.sendData(TweetList);
            }
        });

    }

    private void loadTweetData() {

        miniTweeterViewModel.getListTweet().observe(getActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
               TweetList = tweets;
               myTweetRecyclerViewAdapter.sendData(TweetList);
               swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

        private void sentDataNewAdapter() {

            miniTweeterViewModel.getNewTweet().observe(getActivity(), new Observer<List<Tweet>>() {
                @Override
                public void onChanged(List<Tweet> tweets) {
                    TweetList = tweets;
                    swipeRefreshLayout.setRefreshing(false);
                    myTweetRecyclerViewAdapter.sendData(TweetList);
                    miniTweeterViewModel.getNewTweet().removeObserver(this);
                }
            });

        myTweetRecyclerViewAdapter = new MyTweetRecyclerViewAdapter(
                getActivity(),
                TweetList);
        recyclerView.setAdapter(myTweetRecyclerViewAdapter);
    }
}
