package com.codepath.apps.twitterclient.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TwitterClient;
import com.codepath.apps.twitterclient.TwitterClientApplication;
import com.codepath.apps.twitterclient.fragments.UserTimeLineFragment;
import com.codepath.apps.twitterclient.models.User;
import com.codepath.apps.twitterclient.utils.DateUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient twitterClient;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        twitterClient = TwitterClientApplication.getRestClient();
        String screenName = getIntent().getStringExtra("screen_name");
        if(screenName != null){
            populateOtherUserInfo(screenName);
        }else{
            populateLoggedInUserInfo();
        }
        if(savedInstanceState == null) {
            UserTimeLineFragment userTimeLine = UserTimeLineFragment.newInstance(screenName);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, userTimeLine);
            fragmentTransaction.commit();
        }
    }

    private void populateOtherUserInfo(String screenName){
        twitterClient.getUserInfo(screenName,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    user = User.fromJSON(response.getJSONObject(0));
                    getSupportActionBar().setTitle(user.getUserName());
                    populateUserInfo(user);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void populateLoggedInUserInfo(){
        twitterClient.getUserInfo(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.getUserName());
                    populateUserInfo(user);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateUserInfo(User user){

        ImageView ivUserProfileImg = (ImageView) findViewById(R.id.ivProfileImg);
        TextView tvUserName = (TextView) findViewById(R.id.tvProfileUserName);
        TextView tvUserTagLine = (TextView) findViewById(R.id.tvUserTagLine);
        TextView tvUserFollowers = (TextView) findViewById(R.id.tvUserFollowers);
        TextView tvUserFollowing = (TextView) findViewById(R.id.tvUserFollowing);

        tvUserName.setText(user.getUserName());
        tvUserTagLine.setText(user.getTagLine());
        Picasso.with(this).load(user.getProfileImageURL()).into(ivUserProfileImg);
        tvUserFollowers.setText("" + user.getNoOfFollowers() + " Followers     ");
        tvUserFollowing.setText(""+user.getFriendsCount()+ " Following ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
