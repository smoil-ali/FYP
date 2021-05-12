package com.reactive.fyp.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.reactive.fyp.Interfaces.CategoryListener;
import com.reactive.fyp.Interfaces.SizeListener;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.CategoryLayoutBinding;
import com.reactive.fyp.databinding.SizeLayoutBinding;

public class CategoryFragment extends DialogFragment {
    final String TAG = CategoryFragment.class.getSimpleName();
    CategoryLayoutBinding binding;
    CategoryListener listener;

    public void setListener(CategoryListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.category_layout,container,false);

        binding.done.setOnClickListener(v -> {
            dismiss();
            listener.onCategory(getRadioText(),getSleeve());
        });

        binding.cancel.setOnClickListener(v -> {
            dismiss();
        });

        return binding.getRoot();
    }

    String getRadioText(){
        MaterialRadioButton radioButton = (MaterialRadioButton)binding.getRoot().
                findViewById(binding.genderContainer.getCheckedRadioButtonId());
        return radioButton.getText().toString();
    }

    String getSleeve(){
        MaterialRadioButton radioButton = (MaterialRadioButton)binding.getRoot().
                findViewById(binding.sleeveContainer.getCheckedRadioButtonId());
        return radioButton.getText().toString();
    }
}
