package com.example.outfitter;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostSingleton {

    private static PostSingleton sInstance;

    private DatabaseReference mDatabase;
    List<Post> posts = new ArrayList<>();


    private PostSingleton(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mDatabase
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post p = (Post) snapshot.getValue();
                            posts.add(p);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public static PostSingleton get(Context context) {
        if (sInstance == null) {
            sInstance = new PostSingleton(context);
        }
        return sInstance;
    }
    public void updatePosts() {
        mDatabase
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post p = (Post) snapshot.getValue();
                            posts.add(p);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(String username, String frontImage, List<String> outfitImages) {
        String key = mDatabase.push().getKey();
        Log.d("Singleton", "success upload outfit");
        HashMap map = new HashMap();
        for (int i = 0; i < outfitImages.size(); i++) {
            map.put("image_url" + i, outfitImages.get(i));
        }
        Post p = new Post(username, frontImage, map);
        mDatabase.push().setValue(p);
    }


}
