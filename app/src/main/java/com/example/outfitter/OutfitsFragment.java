package com.example.outfitter;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.fragment.app.Fragment;

public class OutfitsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_outfits,
                container, false);

        LinearLayout fl = layoutView.findViewById(R.id.linear_layout);

        List<List<String>> outfitsUris = OutfitSingleton.get(ClosetActivity.getContextOfApplication()).getOutfitsUris();
        // create a RelativeLayout

        for (int i = 0; i < outfitsUris.size(); i++) {
            MyAdapter adapter = new MyAdapter(ClosetActivity.getContextOfApplication(), outfitsUris.get(i));


            // create a gridview
            GridView gridView= new GridView(ClosetActivity.getContextOfApplication());
            gridView.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 375 + (outfitsUris.get(i).size() - 1) / 3 * 375));

            gridView.setNumColumns(3);

            gridView.setAdapter(adapter);
            fl.addView(gridView);

        }
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