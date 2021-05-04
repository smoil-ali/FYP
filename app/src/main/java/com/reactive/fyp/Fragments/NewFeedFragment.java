package com.reactive.fyp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Adapter.PostAdapter;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.databinding.FragmentNewFeedBinding;
import com.reactive.fyp.model.PostClass;

import java.util.ArrayList;
import java.util.List;


public class NewFeedFragment extends Fragment {



    final String TAG = NewFeedFragment.class.getSimpleName();
    FragmentNewFeedBinding binding;
    PostAdapter adapter;
    List<PostClass> list = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.POST);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_new_feed,container,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.setVisibility(false);
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new PostAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPost();
    }

    void getPost(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    PostClass postClass = snapshot1.getValue(PostClass.class);
                    postClass.setId(snapshot1.getKey());
                    list.add(postClass);
                    Log.i(TAG,postClass.toString());
                }
                binding.setVisibility(true);
                adapter.notifyDataSetChanged();
                if (list.size() > 0){
                    binding.placeHolder.getRoot().setVisibility(View.GONE);
                }else {
                    binding.placeHolder.getRoot().setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG,error.getMessage());
                binding.setVisibility(true);
                binding.nodata.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }
}