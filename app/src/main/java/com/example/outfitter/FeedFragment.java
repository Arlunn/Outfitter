package com.example.outfitter;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedFragment extends Fragment {

    private LinearLayout mLayout;

    private List<Post> feed;

    private PostSingleton mPostSingleton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LayoutInflater vi = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.fragment_feed, null);

        ViewGroup insertPoint = (ViewGroup) v.findViewById(R.id.post_feed);

        mPostSingleton = PostSingleton.get(getActivity().getApplicationContext());

        mPostSingleton.updatePosts();



        //Virtual Outfit will be an arraylist of multiple image strings -> create a grid view of these images
        feed = mPostSingleton.getPosts();


        for(int j = 0; j < feed.size(); j++){
            Post p = feed.get(j);
            View view = inflater.inflate(R.layout.post_view, null);
            String username = p.user;
            TextView user = (TextView) view.findViewById(R.id.username);
            user.setText(username);

            String img = p.front;
            ImageView image = (ImageView) view.findViewById(R.id.postImage);
            Picasso.get().load(img).into(image);

            insertPoint.addView(view, j, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));


        }

        return v;
    }

}
