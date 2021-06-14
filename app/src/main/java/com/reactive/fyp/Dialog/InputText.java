package com.reactive.fyp.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.reactive.fyp.Interfaces.InputTextListener;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.ItemInputText2Binding;
import com.reactive.fyp.databinding.SizeLayoutBinding;

public class InputText extends DialogFragment {

    final String TAG = InputText.class.getSimpleName();
    EditText input_text;
    TextView button;
    InputTextListener listener;
    ItemInputText2Binding binding;

    public InputText() {
    }

    public void setListener(InputTextListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_input_text2,container,false);


        binding.inputText.setHintTextColor(ContextCompat.getColor(getContext(),R.color.white));
        binding.done.setOnClickListener(v -> {
            dismiss();
            listener.onInputText(binding.inputText.getText().toString());
        });

        binding.cancel.setOnClickListener(v -> {
            dismiss();
        });
        return binding.getRoot();
    }

}
