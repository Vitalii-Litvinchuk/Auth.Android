package com.example.newmail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.newmail.constants.Urls;
import com.example.newmail.network.ConnectionDetector;
import com.example.newmail.simplification.EasierActivity;
import com.example.newmail.network.ImageRequester;
import com.example.newmail.network.request.DTOs.ImageDTOs.ImageDTO;
import com.example.newmail.network.request.DTOs.ImageDTOs.ImageResponseDTO;
import com.example.newmail.network.request.RequestService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends EasierActivity {
    private ImageRequester imageRequester;
    private NetworkImageView myImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    // One Preview Image
    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRequester = ImageRequester.getInstance();
        myImage = findViewById(R.id.myimgRegister);
        String urlImg = Urls.BASE + "/images/1.jpg";
        imageRequester.setImageFromUrl(myImage, urlImg);
//        HomeApplication.getInstance().deleteToken();
        IVPreviewImage = findViewById(R.id.IVPreviewImage);
    }

    public void onSelectImage(View view) {
        imageChooser();
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri uri = data.getData();
                // update the preview image in the layout
                IVPreviewImage.setImageURI(uri);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // initialize byte stream
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                // Initialize byte array
                byte[] bytes = stream.toByteArray();
                // get base64 encoded string
                String sImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImage(sImage);

                RequestService.getInstance().jsonImageApi().uploadImage(imageDTO)
                        .enqueue(new Callback<ImageResponseDTO>() {
                            @Override
                            public void onResponse(Call<ImageResponseDTO> call, Response<ImageResponseDTO> response) {
                                ImageResponseDTO data = response.body();
                                String urlImg = Urls.BASE + "/images/" + data.getFilename();
                                imageRequester.setImageFromUrl(myImage, urlImg);
                            }

                            @Override
                            public void onFailure(Call<ImageResponseDTO> call, Throwable t) {
                            }
                        });
            }
        }
    }
}