package com.example.outfitter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;

public class PostSingleton {

    private static PostSingleton sInstance;

    private DatabaseReference mDatabase;
    List<Post> posts = new ArrayList<>();
    private StorageReference mStorage;

    private PostSingleton(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mStorage = FirebaseStorage.getInstance().getReference().child("images");

        mDatabase
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            posts = new ArrayList<>();
                           HashMap p = (HashMap)snapshot.getValue();
                           posts.add(new Post((String)p.get("username"),(String) p.get("front"), (HashMap<String, String>) p.get("virtualOutfit")));
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
                            HashMap<String, Object> hash = (HashMap<String, Object>) snapshot.getValue();
                            posts.add(new Post((String)hash.get("user"), (String)hash.get("front"), (HashMap) hash.get("virtualOutfit")));

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

    public void addPost(String username, byte[] frontImage, List<String> outfitImages) {
        StorageReference ref =  mStorage.child(String.valueOf(UUID.randomUUID()));
        UploadTask uploadTask = ref.putBytes(frontImage);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String key = mDatabase.push().getKey();
                    Log.d("Singleton", "success upload outfit");
                    HashMap map = new HashMap();
                    for (int i = 0; i < outfitImages.size(); i++) {
                        map.put("image_url" + i, outfitImages.get(i));
                    }
                    Post p = new Post(username, downloadUri.toString(), map);
                    mDatabase.push().setValue(p);
                }
            }
        });
    }


}
