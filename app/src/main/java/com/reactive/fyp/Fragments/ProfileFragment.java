package com.reactive.fyp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Activities.EditprofileActivity;
import com.reactive.fyp.Activities.ProfileActivity;
import com.reactive.fyp.Activities.SignInActivitye;
import com.reactive.fyp.Adapter.ImageAdapter;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;
import com.reactive.fyp.databinding.ActivityProfileBinding;
import com.reactive.fyp.databinding.FragmentNewFeedBinding;
import com.reactive.fyp.databinding.FragmentProfileBinding;
import com.reactive.fyp.model.ImageClass;
import com.reactive.fyp.model.ProfileModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {


    final String TAG = ProfileFragment.class.getSimpleName();
    FragmentProfileBinding binding;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USERS);
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();
    ProfileModel model;
    ImageAdapter adapter;
    List<ImageClass> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        binding.setVisibility(false);
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new ImageAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);
        Log.i(TAG,"create...");


        binding.createShirt.setOnClickListener(v -> {
            openScreen();
        });


        getProfile();
        getImages();

        return binding.getRoot();
    }

    void getProfile(){
        databaseReference = firebaseDatabase.getReference(Constants.USERS);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        model = snapshot.getValue(ProfileModel.class);
                        Log.i(TAG,model.toString());
                        Glide.with(getContext())
                                .load(model.getImage())
                                .placeholder(R.drawable.user)
                                .into(binding.image);
                        binding.name.setText(model.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.setVisibility(true);
                        Log.i(TAG,error.getMessage());
                    }
                });
    }

    void getImages(){
        list.clear();
        databaseReference = firebaseDatabase.getReference(Constants.USER_IMAGES);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            ImageClass imageClass = snapshot1.getValue(ImageClass.class);
                            imageClass.setId(snapshot1.getKey());
                            Log.i(TAG,imageClass.getImage());
                            list.add(imageClass);
                        }
                        Log.i(TAG,list.size()+" size");
                        binding.setVisibility(true);
                        if (list.size() > 0){
                            binding.nodata.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }else
                        {
                            binding.nodata.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.setVisibility(true);
                        Log.i(TAG,error.getMessage());
                    }
                });
    }

    void openScreen(){
        Intent intent = new Intent(getContext(), DesignActivity.class);
        startActivity(intent);
    }



}