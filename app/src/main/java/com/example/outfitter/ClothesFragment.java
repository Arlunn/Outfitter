package com.example.outfitter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class ClothesFragment extends Fragment {

    private static final int GALLERY_REQUEST = 1855;
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 1888;
    private LinearLayout layout;
    private final static String USERNAME_PREFERENCE = "name";
    Button importButton;
    private String username;
    List<String> uriStrings;
    MyAdapter adapter;
    List<Integer> positionsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_clothes, container,
                false);
        //layout = (LinearLayout)layoutView.findViewById(R.id.linear_layout);
        importButton = layoutView.findViewById(R.id.importButton);
        importButton.setOnClickListener(v -> showPictureDialog());
        username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
        uriStrings = AccountSingleton.get(getActivity().getApplicationContext()).getClothesUris();
        GridView gridView = layoutView.findViewById(R.id.gridview);
        adapter=new MyAdapter(getActivity(),uriStrings);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.child(username).child("clothesList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            HashMap<String, String> uriMap = (HashMap<String, String>) snapshot.getValue();
                            if (!uriStrings.contains(uriMap.get("image_url"))) {
                                adapter.addImage(uriMap.get("image_url"));
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        gridView.setAdapter(adapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                positionsList = new ArrayList<>();
                importButton.setText("Select or Capture Image");
                importButton.setOnClickListener(v -> showPictureDialog());
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                importButton.setText("Add Outfit");
                importButton.setOnClickListener(v -> addOutfit());
                mode.setTitle("Select Items");
                mode.setSubtitle("One item selected");
                return true;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub


                int selectCount = gridView.getCheckedItemCount();
                switch (selectCount) {
                    case 1:

                        mode.setSubtitle("One item selected");

                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");

                        break;
                }

                return true;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // TODO Auto-generated method stub

                if(checked)
                    positionsList.add(position);
                else
                    positionsList.remove(position);

                int selectCount = gridView.getCheckedItemCount();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
                        break;
                }

            }
        });


        return layoutView;
    }

    private void addOutfit(){
        List<String> outfitUris = new ArrayList<>();
        for (int i : positionsList) {

            outfitUris.add(uriStrings.get(i));
        }
        OutfitSingleton.get(ClosetActivity.getContextOfApplication()).addOutfit(outfitUris, username);

    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery" };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallery();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Adds the view to the layout
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        uriStrings.add(imageUri.toString());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        AccountSingleton.get(getActivity().getApplicationContext()).addImage(bytes, username);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT);
                    }
                }
                break;
        }

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
            this.notifyDataSetChanged();
        }
    }
}