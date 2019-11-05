package com.example.outfitter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;

public class OutfitSingleton {

    private static OutfitSingleton sInstance;

    private DatabaseReference mDatabase;
    List<List<String>> outfitUris = new ArrayList<>();


    private OutfitSingleton(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    }

    public static OutfitSingleton get(Context context) {
        if (sInstance == null) {
            sInstance = new OutfitSingleton(context);
        }
        return sInstance;
    }
    public void updateOutfitsUris(String username) {
        mDatabase.child(username).child("outfits")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            List<String> oneOutfitUris = new ArrayList<>();
                            HashMap<String, String> uriMap = (HashMap<String, String>) snapshot.getValue();

                            for (int i = 0; i < uriMap.size(); i++) {
                                oneOutfitUris.add(uriMap.get("image_url" + i));
                            }
                            outfitUris.add(oneOutfitUris);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    public List<List<String>> getOutfitsUris() {
        return outfitUris;
    }

    public void addOutfit(List<String> imageUris, String username) {
        Log.d("Singleton", "success upload outfit");
        HashMap map = new HashMap();
        for (int i = 0; i < imageUris.size(); i++) {
            map.put("image_url" + i,imageUris.get(i));
        }
        mDatabase.child(username).child("outfits").push().updateChildren(map);
    }


}
