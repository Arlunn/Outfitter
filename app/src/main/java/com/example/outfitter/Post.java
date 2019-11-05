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

    public String uid;
    public String postid;
    public int fireCount = 0;

    public ArrayList<String> fires = new ArrayList<>();

    Image front;
    Image virtualOutfit;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, Image front, Image virtualOutfit) {
        this.uid = uid;
        this.fireCount = 0;
        this.front = front;
        this.virtualOutfit = virtualOutfit;
    }

    public void Like(String uid){
        if(fires.indexOf(uid) >= 0) {
            unLike(uid);
        }else{
            fireCount++;
            fires.add(uid);
        }
        return;
    }

    public void unLike(String uid){
        if(fires.indexOf(uid) < 0) {
            Like(uid);
        }else{
            fireCount--;
            fires.remove(uid);
        }
        return;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("fireCount", fireCount);
        result.put("fires", fires);
        result.put("front",front);
        result.put("virtualOutfit",virtualOutfit);

        return result;
    }
}