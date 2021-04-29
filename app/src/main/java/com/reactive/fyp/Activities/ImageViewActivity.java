package com.reactive.fyp.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;


public class ImageViewActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        imageView = findViewById(R.id.imageView);
        if (getIntent().getExtras() != null){
            String image = getIntent().getExtras().getString(Constants.PARAMS);
            Glide.with(this)
                    .load(image)
                    .into(imageView);
        }
    }
}