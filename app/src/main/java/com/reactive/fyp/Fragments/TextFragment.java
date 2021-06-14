package com.reactive.fyp.Fragments;

import android.content.Context;
import android.content.res.ObbInfo;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.android.widget.colorpickerseekbar.ColorPickerSeekBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.TextAdapter;
import com.reactive.fyp.Dialog.InputText;
import com.reactive.fyp.Interfaces.InputTextListener;
import com.reactive.fyp.Interfaces.TextClickListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.model.FontModel;
import com.reactive.fyp.model.fontClass;

import java.util.ArrayList;
import java.util.List;

public class
TextFragment extends Fragment implements TextClickListener,InputTextListener {

    final String TAG = TextFragment.class.getSimpleName();
    RecyclerView recyclerView;
    TextAdapter adapter;
    List<String> list = new ArrayList<>();
    List<FontModel> fontList = new ArrayList<>();
    DesignActivity designActivity;
    ColorPickerSeekBar colorPickerSeekBar;
    ProgressBar progressBar;
    boolean isTrack;
    EditText editText;
    FloatingActionButton done;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.text_fragment,container,false);
        designActivity = (DesignActivity)getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        colorPickerSeekBar = view.findViewById(R.id.colorpicker);
        progressBar = view.findViewById(R.id.progress);
        recyclerView.hasFixedSize();
        recyclerView.
                setLayoutManager(new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));


        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        colorPickerSeekBar.setOnColorSeekbarChangeListener(new ColorPickerSeekBar.OnColorSeekBarChangeListener() {
            @Override
            public void onColorChanged(SeekBar seekBar, int color, boolean fromUser) {
                Log.i(TAG,"on change");
                if (isTrack){
                    designActivity.textView.setTextColor(color);
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        loadFonts();

    }

    void loadFonts(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.FONTS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fontList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    FontModel fontClass = snapshot1.getValue(FontModel.class);
                    fontList.add(fontClass);
                }
                Constants.defaultFace= Typeface.createFromAsset(getActivity().getAssets(), "font/"+fontList.get(0).getFontName());
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                setup();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(designActivity, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i(TAG,error.getMessage());
            }
        });
    }

    void loadData(){
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
        list.add("Text");
    }

    @Override
    public void onTextClick(int pos) {
        Constants.defaultFace = Typeface.createFromAsset(getActivity().getAssets(), "font/"+fontList.get(pos).getFontName());
        Constants.FontPrice = Integer.parseInt(fontList.get(pos).getPrice());
        designActivity.textView.setTypeface(Constants.defaultFace);
    }

    void setup(){
        adapter =new TextAdapter(getContext(),list,fontList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        showInputTextDialog();
    }

    private void showInputTextDialog() {
        FragmentManager fm = designActivity.getSupportFragmentManager();
        InputText alertDialog = new InputText();
        alertDialog.setListener(this);
        alertDialog.show(fm, "fragment_inputText");
    }

    @Override
    public void onInputText(String text) {
        if (!text.isEmpty()){
            designActivity.maskViewText.setVisibility(View.VISIBLE);
            designActivity.maskViewText.setRotation(0);
            designActivity.maskViewText.setScaleY(1);
            designActivity.textView.setText(text);
            designActivity.textView.setTypeface(Constants.defaultFace);
            Constants.FontPrice = Integer.parseInt(fontList.get(0).getPrice());
        }
    }
}
