package com.reactive.fyp.Fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reactive.fyp.Activities.DesignActivity;
import com.reactive.fyp.Adapter.ShirtAdapter;
import com.reactive.fyp.Interfaces.ShirtListener;
import com.reactive.fyp.R;

import java.util.ArrayList;
import java.util.List;

public class ShirtsFragment extends Fragment implements ShirtListener {

    RecyclerView recyclerView;
    List<Drawable> list=new ArrayList<>();
    ShirtAdapter adapter;
    DesignActivity designActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =getLayoutInflater().inflate(R.layout.shirts_layout,container,false);
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
        adapter= new ShirtAdapter(requireContext(),list);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
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
}
