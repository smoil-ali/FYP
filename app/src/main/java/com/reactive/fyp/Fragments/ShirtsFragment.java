package com.reactive.fyp.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.android.widget.colorpickerseekbar.ColorPickerSeekBar;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.ShirtAdapter;
import com.reactive.fyp.Adapter.StickerAdapter;
import com.reactive.fyp.Interfaces.ShirtListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.model.ProductModel;
import com.reactive.fyp.model.StickerModel;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;


public class ShirtsFragment extends Fragment implements ShirtListener {

    final String TAG = ShirtsFragment.class.getSimpleName();
    List<ProductModel> list=new ArrayList<>();
    DesignActivity designActivity;
    int currentColor;
    ImageView paint ;
    Button back;
    ColorPickerSeekBar colorPickerSeekBar;
    RecyclerView recyclerView;
    ShirtAdapter adapter;
    ProgressBar progressBar;

    boolean isTrack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =getLayoutInflater().inflate(R.layout.shirts_layout,container,false);
        designActivity=(DesignActivity)getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        recyclerView.hasFixedSize();
        recyclerView.
                setLayoutManager(new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));

        colorPickerSeekBar = view.findViewById(R.id.colorpicker);
        paint = view.findViewById(R.id.paint);
        back = view.findViewById(R.id.back);

        adapter=new ShirtAdapter(requireContext(),list);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        colorPickerSeekBar.setOnColorSeekbarChangeListener(new ColorPickerSeekBar.OnColorSeekBarChangeListener() {
            @Override
            public void onColorChanged(SeekBar seekBar, int color, boolean fromUser) {
                Log.i(TAG,"on change");
                if (isTrack){
                    designActivity.home_shirt.setColorFilter(color, PorterDuff.Mode.OVERLAY);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG,"start tracking");

                isTrack = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG,"stop tracking");
                isTrack = false;
            }
        });

        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    @Override
    public void OnShirtClick(ProductModel model) {
        Constants.DESCRIPTION = model.getCategory();
        Glide.with(getContext())
                .load(model.getImage())
                .into(designActivity.home_shirt);

    }

    private void openDialog(){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), currentColor, false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                designActivity.home_shirt.setColorFilter(color, PorterDuff.Mode.OVERLAY);
                Window win = Objects.requireNonNull(getActivity()).getWindow();
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //win.setStatusBarColor(color);
            }
        });
        dialog.show();
    }

    void getData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.PRODUCTS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        ProductModel model = snapshot1.getValue(ProductModel.class);
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
