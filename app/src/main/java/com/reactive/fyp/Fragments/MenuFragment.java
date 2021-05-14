package com.reactive.fyp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Activities.AboutActivity;
import com.reactive.fyp.Activities.EditprofileActivity;
import com.reactive.fyp.Activities.SignInActivitye;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;
import com.reactive.fyp.databinding.FragmentMenuBinding;
import com.reactive.fyp.model.ProfileModel;


public class MenuFragment extends Fragment {

    final String TAG = MenuFragment.class.getSimpleName();
    FragmentMenuBinding binding;
    ProfileModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_menu,container,false);

        binding.back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        binding.editProfile.setOnClickListener(v -> {
            openEditProfile();
        });



        binding.logout.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Log Out")
                    .setMessage("Do you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logOut();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        });

        binding.about.setOnClickListener(v -> {
            Constants.INFO = Constants.ABOUT;
            openScreen();
        });

        binding.contactUs.setOnClickListener(v -> {
            Constants.INFO = Constants.CONTACT_US;
            openScreen();
        });

        binding.followUs.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Follow us", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProfileData();
    }

    void getProfileData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USERS);
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        model = snapshot.getValue(ProfileModel.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                    }
                });
    }

    void openEditProfile(){
        Intent intent = new Intent(getContext(), EditprofileActivity.class);
        intent.putExtra(Constants.PARAMS,model);
        startActivity(intent);
    }

    void logOut(){
        FirebaseAuth.getInstance().signOut();
        Helper.setLogin(getContext(),false);
        startActivity(new Intent(getContext(), SignInActivitye.class));
        getActivity().finish();
    }

    void openScreen(){
        Intent intent = new Intent(getContext(), AboutActivity.class);
        startActivity(intent);
    }
}