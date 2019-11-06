package com.example.outfitter;

import android.media.Image;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String user;
    public String front;
    public HashMap<String, String> virtualOutfit;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String front, HashMap<String, String> virtualOutfit) {
        this.user = uid;
        this.front = front;
        this.virtualOutfit = virtualOutfit;
    }

}