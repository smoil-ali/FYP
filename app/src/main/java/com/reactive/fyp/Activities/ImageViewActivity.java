package com.reactive.fyp.Activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;
import com.reactive.fyp.databinding.ActivityImageViewBinding;
import com.reactive.fyp.model.ImageClass;
import com.reactive.fyp.model.PostClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImageViewActivity extends AppCompatActivity {

    final String TAG = ImageViewActivity.class.getSimpleName();
    ImageView imageView;
    ActivityImageViewBinding binding;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.POST);
    PostClass postClass;
    ProgressDialog progressDialog;
    SimpleDateFormat simpleDateFormat;
    String date;
    ImageClass imageClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_image_view);
        imageView = findViewById(R.id.imageView);
        postClass = new PostClass();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(Constants.POST);
        progressDialog.setMessage("Wait, while your post is being uploading...");
        simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");

        if (getIntent().getExtras() != null){
            imageClass= (ImageClass) getIntent().getExtras().getSerializable(Constants.PARAMS);
            postClass.setImage(imageClass);
            Glide.with(this)
                    .load(imageClass.getImage())
                    .into(imageView);
        }

        binding.price.setText(imageClass.getPrice()+"-/Rs");

        binding.share.setOnClickListener(v -> {
            date=simpleDateFormat.format(new Date());
            post();
        });

        binding.cart.setOnClickListener(v -> {
            addToCart();
        });

        binding.delete.setOnClickListener(v -> {
            delete();
        });

    }

    void post(){
        progressDialog.show();
        postClass.setPublisherId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        postClass.setTimestamp(date);
        databaseReference.push().setValue(postClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.i(TAG,e.getMessage());
                    }
                });
    }

    void addToCart(){
        String cartdata = Helper.getCartData(this);
        if (!cartdata.isEmpty()){
            List<ImageClass> imageClassList = Helper.fromStringToList(cartdata);
            if (check(imageClassList,imageClass.getId())){
                imageClassList.add(imageClass);
                Helper.setCartData(Helper.fromListToString(imageClassList),this);
                Log.i(TAG,imageClassList.size() +" ");
            }else
                Toast.makeText(this, "Already in cart", Toast.LENGTH_SHORT).show();
        }else {
            List<ImageClass> imageClassList = new ArrayList<>();
            imageClassList.add(imageClass);
            Helper.setCartData(Helper.fromListToString(imageClassList),this);
            Log.i(TAG,imageClassList.size() +" ");
        }
        {
            Toast.makeText(this, "Added To Cart", Toast.LENGTH_SHORT).show();
        }
    }

    void delete(){
        progressDialog.setTitle("Delete");
        progressDialog.setMessage("Wait, while your post is being delete...");
        progressDialog.show();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER_IMAGES);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(imageClass.getId())
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                onBackPressed();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ImageViewActivity.this, "try again", Toast.LENGTH_SHORT).show();
                Log.i(TAG,e.getMessage());
            }
        });


    }

    boolean check(List<ImageClass> imageClassList, String id){
        for (ImageClass imageClass :imageClassList){
            if (imageClass.getId().equals(id)){
                return false;
            }
        }
        return true;
    }
}