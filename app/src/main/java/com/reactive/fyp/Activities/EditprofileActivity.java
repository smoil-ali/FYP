package com.reactive.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;
import com.reactive.fyp.databinding.ActivityEditprofileBinding;
import com.reactive.fyp.model.ProfileModel;

import java.io.IOException;
import java.util.UUID;

public class EditprofileActivity extends AppCompatActivity {

    final String TAG = EditprofileActivity.class.getSimpleName();
    private final int PICK_IMAGE_REQUEST = 71;
    ActivityEditprofileBinding binding;
    ProfileModel model;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USERS);
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    private Uri filePath;
    private String downloadUrl;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_editprofile);
        model = new ProfileModel();
        if (getIntent().getExtras() != null){
            model = (ProfileModel) getIntent().getExtras().getSerializable(Constants.PARAMS);
        }
        binding.setData(model);
        binding.setVisibility(true);
        mAuth = FirebaseAuth.getInstance();

        Glide.with(this).load(model.getImage())
                .placeholder(R.drawable.user)
                .into(binding.image);

        binding.done.setOnClickListener(v -> {
            if (isValid()){
                if (downloadUrl != null)
                    model.setImage(downloadUrl);
                binding.setVisibility(false);
                updateProfile();
            }

        });

        binding.image.setOnClickListener(v -> {
            chooseImage();
        });

    }

    void updateProfile(){
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(model)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.setVisibility(true);
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG,e.getMessage());
                        binding.setVisibility(true);
                        Toast.makeText(EditprofileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    boolean isValid(){
        model.setDisplayError(true);
        if (!model.getNameError().isEmpty()){
            return false;
        }
        if (downloadUrl == null && model.getImage().isEmpty()){
            Toast.makeText(this, "Add Profile Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        model.setDisplayError(false);
        return true;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                binding.image.setImageBitmap(bitmap);
                uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    void uploadImage(){
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(EditprofileActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setMessage("Image is uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isComplete());
                            downloadUrl = uriTask.getResult().toString();
                            progressDialog.dismiss();
                            Toast.makeText(EditprofileActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(EditprofileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}