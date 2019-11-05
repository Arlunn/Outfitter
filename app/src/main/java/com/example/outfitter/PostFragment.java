package com.example.outfitter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PostFragment extends Fragment {
    private static final int GALLERY_REQUEST = 1855;
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 1888;
    private LinearLayout layout;
    private final static String USERNAME_PREFERENCE = "name";
    Button importButton;
    Button selectOutfit;
    Button postButton;
    private String username;
    List<String> uriStrings;
    ClothesFragment.MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_post, container,
                false);
        //layout = (LinearLayout)layoutView.findViewById(R.id.linear_layout);
        importButton = layoutView.findViewById(R.id.importButton);
        importButton.setOnClickListener(v -> showPictureDialog());
        username = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(USERNAME_PREFERENCE, "username");
        uriStrings = AccountSingleton.get(getActivity().getApplicationContext()).getClothesUris();


        selectOutfit = layoutView.findViewById(R.id.selectButton);
        //Select the outfit instead
        selectOutfit.setOnClickListener(v -> showPictureDialog());

        postButton = layoutView.findViewById(R.id.postButton);
        //Add the post to the database instead
        postButton.setOnClickListener(v -> showPictureDialog());

        return layoutView;
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallery();
                            break;
                        case 1:
                            takePhotoFromCamera();
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
    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Adds the view to the layout
        switch (requestCode) {
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    adapter.addImage(photo.toString());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytes = baos.toByteArray();
                    String temp= Base64.encodeToString(bytes, Base64.DEFAULT);
                    adapter.addImage(temp);
                    uriStrings.add(temp);
                    AccountSingleton.get(getActivity().getApplicationContext()).addImage(bytes, username);
                }

                break;
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri imageUri = data.getData();
                        InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        adapter.addImage(imageUri.toString());
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
}
