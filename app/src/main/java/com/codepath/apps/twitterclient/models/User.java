package com.codepath.apps.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akulka2 on 10/24/15.
 */
public class User {

    private Long uid;
    private String userName;
    private String profileImageURL;
    private String tagLine;
    private Long noOfFollowers;
    private Long noOfFollowingUsers;
    private String screenName;

    public String getScreenName() {
        return screenName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public Long getNoOfFollowers() {
        return noOfFollowers;
    }

    public Long getFriendsCount() {
        return noOfFollowingUsers;
    }

    public Long getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public static User fromJSON(JSONObject jsonObject){
        User user = new User();
        try {
            user.uid = jsonObject.getLong("id");
            user.profileImageURL = jsonObject.getString("profile_image_url");
            user.userName = jsonObject.getString("name");
            user.tagLine = jsonObject.getString("description");
            user.noOfFollowers = jsonObject.getLong("followers_count");
            user.noOfFollowingUsers = jsonObject.getLong("friends_count");
            user.screenName = jsonObject.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
