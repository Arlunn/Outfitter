package com.example.outfitter;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.fragment.app.Fragment;

public class OutfitsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_outfits,
                container, false);

        FrameLayout fl = layoutView.findViewById(R.id.frame_layout);

        List<List<String>> outfitsUris = OutfitSingleton.get(ClosetFragmentPager.getContextOfApplication()).getOutfitsUris();
        // create a RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(ClosetFragmentPager.getContextOfApplication());

        // define the RelativeLayout layout parameters.
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,
                RelativeLayout.LayoutParams.FILL_PARENT);
        for (int i = 0; i < outfitsUris.size(); i++) {
            MyAdapter adapter = new MyAdapter(ClosetFragmentPager.getContextOfApplication(), outfitsUris.get(i));


            // create a gridview
            GridView gridView= new GridView(ClosetFragmentPager.getContextOfApplication());

            gridView.setLayoutParams(new GridView.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
            gridView.setNumColumns(3);

            gridView.setAdapter(adapter);

            // Adding the gridview to the RelativeLayout as a child
            relativeLayout.addView(gridView);
        }
        fl.addView(relativeLayout);
       return layoutView;
    }


    public class MyAdapter extends ArrayAdapter<String> {

        private List<String> images;
        private Context context;

        public MyAdapter(Context context,
                         List<String> images) {
            super(context,0, images);
            this.images = images;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(getActivity().getApplicationContext());
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(300,300);
            layoutParams.gravity= Gravity.CENTER_VERTICAL;
            image.setLayoutParams(layoutParams);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setMaxHeight(750);
            image.setMaxHeight(750);
            Picasso.get().load(images.get(position)).into(image);
            return image;
        }

        public void addImage(String image) {
            images.add(image);
            this.notifyDataSetChanged();
        }
    }

}