package com.reactive.fyp.Fragments;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.StickerAdapter;
import com.reactive.fyp.Interfaces.StickerListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.FileNames;

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
    List<Bitmap> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=getLayoutInflater().inflate(R.layout.sticker_layout,container,false);
        designActivity=(DesignActivity)getActivity();
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.
                setLayoutManager(new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadList();
        adapter=new StickerAdapter(requireContext(),list);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }
    void loadList(){
        for (String fileNames1: new FileNames().names){
            Log.i(TAG,fileNames1);
            list.add(getBitmapFromAssets(fileNames1));
        }
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = Objects.requireNonNull(getContext()).getAssets();
        Bitmap bitmap=null;
        InputStream istr;
        try {
            istr = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(istr);
            istr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void OnStickerClick(Bitmap bitmap) {
        designActivity.home_sticker.setImageBitmap(bitmap);
    }
}
