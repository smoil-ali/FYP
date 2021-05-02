package com.reactive.fyp.Fragments;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.StickerAdapter;
import com.reactive.fyp.Interfaces.StickerListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.FileNames;
import com.reactive.fyp.model.StickerModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StickerFragment extends Fragment implements StickerListener {

    final String TAG = StickerFragment.class.getSimpleName();
    RecyclerView recyclerView;
    StickerAdapter adapter;
    DesignActivity designActivity;
    List<StickerModel> list=new ArrayList<>();
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=getLayoutInflater().inflate(R.layout.sticker_layout,container,false);
        designActivity=(DesignActivity)getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        recyclerView.hasFixedSize();
        recyclerView.
                setLayoutManager(new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));

        adapter=new StickerAdapter(requireContext(),list);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    @Override
    public void OnStickerClick(StickerModel model) {
        designActivity.maskView.setVisibility(View.VISIBLE);
        designActivity.maskView.setRotation(0);
        Glide.with(getContext()).load(model.getImageUrl()).into(designActivity.home_sticker);
        designActivity.home_sticker.setScaleY(1);
        Constants.stickerPrice = Integer.parseInt(model.getPrice());
    }


    void getData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.STICKERS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        StickerModel model = snapshot1.getValue(StickerModel.class);
                        Log.i(TAG,model.getPrice());
                        list.add(model);
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    if (list.size() > 0){
                        Log.i(TAG,list.size()+"");
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(designActivity, "Data not Available", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(designActivity, "Data not Available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(designActivity, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.i(TAG,error.getMessage());
            }
        });
    }
}
