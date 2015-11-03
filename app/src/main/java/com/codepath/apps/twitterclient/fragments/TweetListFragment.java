package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.adapters.TweetItemAdapter;
import com.codepath.apps.twitterclient.listeners.EndlessScrollListener;
import com.codepath.apps.twitterclient.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akulka2 on 10/31/15.
 */
public abstract class TweetListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private ListView lvHomeTimeline;
    private TweetItemAdapter tweetItemAdapter;

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        tweetItemAdapter = new TweetItemAdapter(getActivity(),tweets);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvHomeTimeline = (ListView) v.findViewById(R.id.lvHomeTimeline);
        lvHomeTimeline.setAdapter(tweetItemAdapter);
        setupOnScrollListener();
        return  v;
    }

    public void addAll(List<Tweet> tweets){
        tweetItemAdapter.addAll(tweets);
    }

    private void setupOnScrollListener(){
        lvHomeTimeline.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView

                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    // Append more data into the adapter
    public abstract void customLoadMoreDataFromApi(int offset) ;
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter



}
