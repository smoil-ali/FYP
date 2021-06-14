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
import com.reactive.fyp.Interfaces.SizeListener;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.SizeLayoutBinding;

public class SizeFragment extends DialogFragment {

    final String TAG = SizeFragment.class.getSimpleName();
    SizeLayoutBinding binding;
    SizeListener listener;

    public void setListener(SizeListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.size_layout,container,false);

        binding.done.setOnClickListener(v -> {
            dismiss();
            listener.OnSize(getRadioText());
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
}
