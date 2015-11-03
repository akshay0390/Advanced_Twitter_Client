package com.codepath.apps.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "GVcSlcOb2h0igvAWRVEDGKiHT";       // Change this
	public static final String REST_CONSUMER_SECRET = "Kz1S2WWaO7jEqkcP8Wi3zopfujmDZgCpVBju5SvGcvxjVcARzV"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://tweetomania"; // Change this (here and in manifest)

	private static final String HOME_TIME_LINE_URL = "statuses/home_timeline.json";
	private static final String USER_INFO_URL = "account/verify_credentials.json";
	private static final String POST_TWEET_URL = "statuses/update.json";
	private static final String MENTIONS_TWEET_URL = "statuses/mentions_timeline.json";
    private static final String USER_TIMELINE = "statuses/user_timeline.json";
    private static final String LOOKUP_USER = "users/lookup.json";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	public void getHomeTimeLine(AsyncHttpResponseHandler handler, Long max_id){
		String apiURL = getApiUrl(HOME_TIME_LINE_URL);
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("since_id",1);
		if(max_id!=null){
			params.put("max_id", max_id);
		}
		client.get(apiURL, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl(USER_INFO_URL);
		client.get(apiURL,null,handler);
	}

    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler){
        String apiURL = getApiUrl(LOOKUP_USER);
        RequestParams params = new RequestParams();
		if(screenName!=null) {
			params.add("screen_name", screenName);
		}
        client.get(apiURL,params,handler);
    }

	public void postTweet(AsyncHttpResponseHandler handler, String tweetBody){
		String apiURL = getApiUrl(POST_TWEET_URL);
		RequestParams params = new RequestParams();
		params.put("status",tweetBody);
		client.post(apiURL, params, handler);
	}

	public void getMentionsTimeLine(AsyncHttpResponseHandler handler, Long maxId) {
        String apiURL = getApiUrl(MENTIONS_TWEET_URL);
        RequestParams params = new RequestParams();
        params.put("count",25);
        params.put("since_id",1);
        if(maxId!=null){
            params.put("max_id", maxId);
        }
        client.get(apiURL, params, handler);
	}

    public void getUserTimeLine(String screenName, AsyncHttpResponseHandler handler){
        String apiURL = getApiUrl(USER_TIMELINE);
        RequestParams params = new RequestParams();
        params.put("screen_name",screenName);
        params.put("count",25);
        client.get(apiURL, params, handler);
    }
}