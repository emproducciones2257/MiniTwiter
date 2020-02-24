package com.emproducciones.minitwiter.UI;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.*;
import androidx.recyclerview.widget.*;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.*;
import com.emproducciones.minitwiter.MyTweetRecyclerViewAdapter;
import com.emproducciones.minitwiter.R;
import com.emproducciones.minitwiter.Retrofit.Response.Tweet;
import com.emproducciones.minitwiter.data.MiniTweeterViewModel;
import java.util.List;

public class TweetListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    MyTweetRecyclerViewAdapter myTweetRecyclerViewAdapter;
    List<Tweet> TweetList;
    MiniTweeterViewModel miniTweeterViewModel;
    SwipeRefreshLayout swipeRefreshLayout;

    public TweetListFragment() {
    }

    public static TweetListFragment newInstance(int columnCount) {
        TweetListFragment fragment = new TweetListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miniTweeterViewModel = ViewModelProviders.of(getActivity())
                .get(MiniTweeterViewModel.class);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
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
                    sentDataNewAdapter();
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            myTweetRecyclerViewAdapter = new MyTweetRecyclerViewAdapter(
                    getActivity(),
                    TweetList);
            recyclerView.setAdapter(myTweetRecyclerViewAdapter);

            sentDataAdapter();

        return view;
    }

    private void sentDataAdapter() {

        miniTweeterViewModel.getListTweet().observe(getActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
               TweetList = tweets;
               myTweetRecyclerViewAdapter.sendData(TweetList);
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
