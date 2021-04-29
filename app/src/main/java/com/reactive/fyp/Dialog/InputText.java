package com.reactive.fyp.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.reactive.fyp.Interfaces.InputTextListener;
import com.reactive.fyp.R;

public class InputText extends DialogFragment {

    final String TAG = InputText.class.getSimpleName();
    EditText input_text;
    Button button;
    InputTextListener listener;

    public InputText() {
    }

    public void setListener(InputTextListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_text_layout,null,false);
        input_text = view.findViewById(R.id.input_text);
        button = view.findViewById(R.id.done);

        button.setOnClickListener(v -> {
            dismiss();
            listener.onInputText(input_text.getText().toString());
        });
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
