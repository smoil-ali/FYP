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
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.android.widget.colorpickerseekbar.ColorPickerSeekBar;
import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.ShirtAdapter;
import com.reactive.fyp.Interfaces.ShirtListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;


public class ShirtsFragment extends Fragment implements ShirtListener {

    final String TAG = ShirtsFragment.class.getSimpleName();
    List<Drawable> list=new ArrayList<>();
    DesignActivity designActivity;
    int currentColor;
    ImageView paint ;
    Button front,back,roundShirt,vShirt,fullsleeve,halfsleeve;
    ColorPickerSeekBar colorPickerSeekBar;
    boolean isTrack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =getLayoutInflater().inflate(R.layout.shirts_layout,container,false);
        designActivity=(DesignActivity)getActivity();

        colorPickerSeekBar = view.findViewById(R.id.colorpicker);
        paint = view.findViewById(R.id.paint);
        front = view.findViewById(R.id.front);
        back = view.findViewById(R.id.back);
        roundShirt = view.findViewById(R.id.roundshirt);
        vShirt = view.findViewById(R.id.vshirt);
        fullsleeve = view.findViewById(R.id.fullsleeve);
        halfsleeve = view.findViewById(R.id.halfsleeve);
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

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.DIRECTION_MSG = "Front";
                designActivity.home_shirt
                        .setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                        R.drawable.tshirt_v_template));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.DIRECTION_MSG = "Back";
                designActivity.home_shirt
                        .setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                        R.drawable.tshirt_kids_template));
            }
        });

        roundShirt.setOnClickListener(v -> {
            Constants.TYPE_MSG = "Round";
            designActivity.home_shirt
                    .setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                            R.drawable.tshirt_template));
        });

        vShirt.setOnClickListener(v -> {
            Constants.TYPE_MSG = "V";
            designActivity.home_shirt
                    .setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                            R.drawable.tshirt_v_template));
        });

        fullsleeve.setOnClickListener(v -> {
            Constants.SLEEVE_MSG = "Full Sleeve";
            designActivity.home_shirt
                    .setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                            R.drawable.fsleeve));
        });

        halfsleeve.setOnClickListener(v -> {
            Constants.SLEEVE_MSG = "Half Sleeve";
            designActivity.home_shirt
                    .setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                            R.drawable.tshirt_template));
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    void loadList(){
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt1));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt2));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt3));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt4));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt5));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt6));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt7));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt8));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt9));
        list.add(ContextCompat.getDrawable(requireContext(),R.drawable.shirt10));
    }

    @Override
    public void OnShirtClick(Drawable drawable) {
        designActivity.home_shirt.setImageDrawable(drawable);
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
}
