package com.reactive.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.reactive.fyp.Adapter.ImageAdapter;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.databinding.ActivityProfileBinding;
import com.reactive.fyp.model.ImageClass;
import com.reactive.fyp.model.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    final String TAG = ProfileActivity.class.getSimpleName();
    ActivityProfileBinding binding;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USERS);
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    ProfileModel model;
    ImageAdapter adapter;
    List<ImageClass> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(new GridLayoutManager(this,4));
        adapter = new ImageAdapter(this,list);
        binding.recycler.setAdapter(adapter);
        Log.i(TAG,"create...");
        getProfile();
        getImages();
    }

    void getProfile(){
        databaseReference = firebaseDatabase.getReference(Constants.USERS);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        model = snapshot.getValue(ProfileModel.class);
                        Log.i(TAG,model.toString());
                        Glide.with(ProfileActivity.this)
                                .load(model.getImage())
                                .placeholder(R.drawable.user)
                                .into(binding.image);
                        binding.name.setText(model.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                    }
                });
    }

    void getImages(){
        list.clear();
        databaseReference = firebaseDatabase.getReference(Constants.USER_IMAGES);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            ImageClass imageClass = snapshot1.getValue(ImageClass.class);
                            Log.i(TAG,imageClass.getImage());
                            list.add(imageClass);
                        }
                        Log.i(TAG,list.size()+" size");
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                    }
                });
    }

}