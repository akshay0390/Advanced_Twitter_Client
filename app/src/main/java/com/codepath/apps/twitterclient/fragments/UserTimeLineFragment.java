package com.codepath.apps.twitterclient.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.TwitterClientApplication;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by akulka2 on 10/31/15.
 */
public class UserTimeLineFragment extends TweetListFragment {

    private TwitterClient twitterClient;

    public static UserTimeLineFragment newInstance(String screenName) {
        UserTimeLineFragment fragmentDemo = new UserTimeLineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterClient = TwitterClientApplication.getRestClient();
        populateUserTimeLine();
    }

    @Override
    public void customLoadMoreDataFromApi(int offset) {
        populateUserTimeLine();
    }

    private void populateUserTimeLine(){
        String screenName = getArguments().getString("screen_name");
        twitterClient.getUserTimeLine(screenName,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("error", throwable.getMessage());
                Toast.makeText(getActivity(), "Error occurred while retrieving Tweets", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
