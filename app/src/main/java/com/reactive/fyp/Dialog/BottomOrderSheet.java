package com.reactive.fyp.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.AbsSpinnerBindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.reactive.fyp.Adapter.BottomSheetAdapter;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.BottomSheetOrdersBinding;
import com.reactive.fyp.model.ImageClass;

import java.util.List;

public class BottomOrderSheet extends BottomSheetDialogFragment {
    final String TAG = BottomSheetDialog.class.getSimpleName();
    BottomSheetOrdersBinding binding;
    BottomSheetAdapter adapter;
    List<ImageClass> list;

    public void setList(List<ImageClass> list) {
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_orders,container,false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.recycler.hasFixedSize();
        adapter = new BottomSheetAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);


        return binding.getRoot();
    }
}
