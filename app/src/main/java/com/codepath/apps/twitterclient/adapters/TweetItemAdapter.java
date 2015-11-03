package com.codepath.apps.twitterclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.activities.ProfileActivity;
import com.codepath.apps.twitterclient.models.Tweet;
import com.codepath.apps.twitterclient.utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akulka2 on 10/24/15.
 */
public class TweetItemAdapter extends ArrayAdapter<Tweet> {

    private TweetItemAdapter adapter;

    public TweetItemAdapter(Context context, List<Tweet> objects) {
        super(context,android.R.layout.simple_list_item_1 , objects);
        this.adapter = this;
    }

    private static class ViewHolder{

        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvTweetBody;
        TextView tvRelativeTimestamp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Tweet tweet = getItem(position);
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item,parent,false);
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvTweetBody = (TextView) convertView.findViewById(R.id.tvTweetBody);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvRelativeTimestamp = (TextView) convertView.findViewById(R.id.tvRelativeTime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =  (ViewHolder) convertView.getTag();
        }

        viewHolder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screenName = tweet.getUser().getScreenName();
                Log.i("Screen name",screenName);
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name",screenName);
                getContext().startActivity(i);
            }
        });

        viewHolder.tvUserName.setText(tweet.getUser().getUserName());
        viewHolder.tvTweetBody.setText(tweet.getBody());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(viewHolder.ivProfileImage);
        String relativeTime = DateUtil.getRelativeTimeAgo(tweet.getCreatedAt());
        viewHolder.tvRelativeTimestamp.setText(relativeTime);
        return convertView;
    }


}
