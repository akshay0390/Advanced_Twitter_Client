package com.codepath.apps.twitterclient.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TwitterClientApplication;
import com.codepath.apps.twitterclient.fragments.HomeTweetsFragment;
import com.codepath.apps.twitterclient.fragments.MentionsTweetFragment;
import com.codepath.apps.twitterclient.models.User;

import org.scribe.builder.api.TwitterApi;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.model.Token;
import org.scribe.oauth.OAuth10aServiceImpl;

public class HomeTimeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new TweetPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        slidingTabStrip.setViewPager(viewPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_timeline, menu);
        //ActionBar mActionBar = getSupportActionBar();
        //mActionBar.setDisplayOptions( ActionBar.DISPLAY_SHOW_HOME| ActionBar.DISPLAY_SHOW_TITLE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.tweet) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showTweetActivity(MenuItem item) {
        Intent i = new Intent(this, TweetActivity.class);
        startActivity(i);
    }

    public void showProfileView(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }




    public class TweetPagerAdapter extends FragmentPagerAdapter{

        private String TABS[] = {"Home","Mentions"};

        public TweetPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new HomeTweetsFragment();
            }else if(position == 1){
                return new MentionsTweetFragment();
            }else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }

        @Override
        public int getCount() {
            return TABS.length;
        }
    }
}
