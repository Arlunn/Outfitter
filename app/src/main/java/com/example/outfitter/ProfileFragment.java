package com.example.outfitter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.outfitter.LoginFragment.TAG;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private final static String USERNAME_PREFERENCE = "name";

    private TextView mUsernameTextView;
    private String username;
    List<String> uriStrings;
    //ProfileFragment.MyAdapter adapter;
    List<Integer> positionsList = new ArrayList<>();
    private ImageView imageView;
    private LinearLayout layout;
    private int totalPosts = 0;
    private PostSingleton mPostSingleton;
    private List<Post> toInsert;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);

        username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
        mUsernameTextView = (TextView) v.findViewById(R.id.text_profile);
        //mUsernameTextView.setText(username);
        /**
         Button mDeleteAccount = (Button) v.findViewById(R.id.deleteAccountButton);
         mDeleteAccount.setOnClickListener(this);
         Button mUpdtePassword = (Button) v.findViewById(R.id.updatePasswordButton);
         mUpdtePassword.setOnClickListener(this);
         Button mSignOut = (Button) v.findViewById(R.id.signOutButton);
         mSignOut.setOnClickListener(this);
         **/

        Button mSetting = (Button) v.findViewById(R.id.settings);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AccountSettingActivity.class));
            }
        });
        Button mCloset = (Button) v.findViewById(R.id.closet);
        mCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ClosetFragmentPager.class));
            }
        });

        GridView gridView = v.findViewById(R.id.gridview);
        //adapter= new MyAdapter(getActivity(),uriStrings);
        //gridView.setAdapter(adapter);

        mPostSingleton = PostSingleton.get(getActivity().getApplicationContext());

        mPostSingleton.updatePosts();

        toInsert = mPostSingleton.getPosts();
        /**
        for(int i =0; i<toInsert.size(); i++){
            Post p = toInsert.get(i);
            String postUsername = p.user;
            if (postUsername.equals(username)) {
                totalPosts++;
            }

        }**/
        if (toInsert.size()>=1)
            Picasso.get().load(toInsert.get(0).front).into((ImageView) v.findViewById(R.id.image1));
        if(toInsert.size() >=2)
            Picasso.get().load(toInsert.get(1).front).into((ImageView) v.findViewById(R.id.image2));
        if (toInsert.size() >=3)
            Picasso.get().load(toInsert.get(2).front).into((ImageView) v.findViewById(R.id.image3));
        if (toInsert.size() >=4)
            Picasso.get().load(toInsert.get(3).front).into((ImageView) v.findViewById(R.id.image4));

        return v;
    }

    @Override
    public void onClick(View v) {
        /**
         switch (v.getId()) {
         case R.id.deleteAccountButton:
         SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
         SharedPreferences.Editor editor = settings.edit();
         editor.remove(USERNAME_PREFERENCE);
         editor.commit();

         AccountSingleton.get(getContext()).deleteUser(username);

         // Bring up login screen again
         startActivity(new Intent(getActivity(), LoginActivity.class));
         getActivity().finish();
         break;
         case R.id.updatePasswordButton:
         ((FragmentChangeInterface) getActivity()).loadFragment(new UpdatePasswordFragment());
         break;
         case R.id.signOutButton:
         settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
         editor = settings.edit();
         editor.remove(USERNAME_PREFERENCE);
         editor.commit();

         startActivity(new Intent(getActivity(), LoginActivity.class));
         getActivity().finish();
         break;
         }
         **/
    }
    /**
     private void setupToolbar(){
     Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
     setSupportActionBar(toolbar);

     ImageView profileMenu = (ImageView) findViewById(R.id.profileMenu);
     profileMenu.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    Log.d(TAG, "onClick: account setting");
    Intent intent = new Intent(mContext, AccountSettingActivity.class);
    startActivity(intent);
    }
    });
     }

     private void setSupportActionBar(Toolbar toolbar) {
     }
     **/

    private void addOutfit(){
        List<String> postUri = new ArrayList<>();

    }

    /**
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
        **/
}
