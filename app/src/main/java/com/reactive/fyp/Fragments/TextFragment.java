package com.reactive.fyp.Fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.android.widget.colorpickerseekbar.ColorPickerSeekBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.TextAdapter;
import com.reactive.fyp.Interfaces.TextClickListener;
import com.reactive.fyp.R;

import java.util.ArrayList;
import java.util.List;

public class TextFragment extends Fragment implements TextClickListener {

    final String TAG = TextFragment.class.getSimpleName();
    EditText text;
    RecyclerView recyclerView;
    TextAdapter adapter;
    List<String> list = new ArrayList<>();
    List<Integer> fontList = new ArrayList<Integer>();
    DesignActivity designActivity;
    FloatingActionButton fab;
    ColorPickerSeekBar colorPickerSeekBar;
    boolean isTrack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.text_fragment,container,false);
        designActivity = (DesignActivity)getActivity();
        text = view.findViewById(R.id.text);
        fab = view.findViewById(R.id.fab);
        recyclerView=view.findViewById(R.id.recyclerView);
        colorPickerSeekBar = view.findViewById(R.id.colorpicker);
        recyclerView.hasFixedSize();
        recyclerView.
                setLayoutManager(new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));

        fab.setOnClickListener(v -> {
            if (!text.getText().toString().trim().matches("")){
                designActivity.maskViewText.setVisibility(View.VISIBLE);
                designActivity.maskViewText.setRotation(0);
                designActivity.maskViewText.setScaleY(1);
                designActivity.textView.setText(text.getText().toString());
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        });

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
        adapter =new TextAdapter(getContext(),list,fontList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

    }

    void loadFonts(){
        fontList.add(R.font.ffont1);
        fontList.add(R.font.ffont2);
        fontList.add(R.font.ffont3);
        fontList.add(R.font.ffont4);
        fontList.add(R.font.ffont5);
        fontList.add(R.font.ffont6);
        fontList.add(R.font.ffont7);
        fontList.add(R.font.ffont8);
        fontList.add(R.font.ffont9);
        fontList.add(R.font.ffont10);
        fontList.add(R.font.ffont11);
        fontList.add(R.font.ffont12);
        fontList.add(R.font.ffont13);
        fontList.add(R.font.ffont14);
        fontList.add(R.font.ffont15);
        fontList.add(R.font.ffont16);
        fontList.add(R.font.ffont17);
        fontList.add(R.font.ffont18);
        fontList.add(R.font.ffont19);
        fontList.add(R.font.ffont20);
        fontList.add(R.font.ffont21);
        fontList.add(R.font.ffont22);
        fontList.add(R.font.ffont23);
        fontList.add(R.font.ffont24);
        fontList.add(R.font.ffont25);
        fontList.add(R.font.ffont26);
        fontList.add(R.font.ffont27);
        fontList.add(R.font.ffont28);
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
        designActivity.textView.setTypeface(ResourcesCompat.getFont(getContext(),fontList.get(pos)));
    }
}
