package com.example.outfitter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;

public class FeedFragment extends Fragment {

    private LinearLayout mLayout;

    private ArrayList<Post> feed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LayoutInflater vi = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.fragment_feed, null);

        ViewGroup insertPoint = (ViewGroup) v.findViewById(R.id.post_feed);

        Post sample = new Post("Angela", null , null);

        //Virtual Outfit will be an arraylist of multiple image strings -> create a grid view of these images
        feed = new ArrayList<>();
        feed.add(sample);


        for(Post post : feed){
            View view = inflater.inflate(R.layout.post_view, null);
            Map<String, Object> result = post.toMap();
            String username = (String) result.get("uid");
            TextView user = (TextView) view.findViewById(R.id.username);
            user.setText(username);
            insertPoint.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }

        return v;
    }

}
