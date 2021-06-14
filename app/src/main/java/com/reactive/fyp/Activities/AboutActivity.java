package com.reactive.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    final String TAG = AboutActivity.class.getSimpleName();
    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_about);


        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));

        if (Constants.INFO.equals(Constants.ABOUT)){
            binding.text.setVisibility(View.VISIBLE);
            binding.text.setText(getString(R.string.aboutApp));
            binding.title.setText("About");
        }

        if (Constants.INFO.equals(Constants.CONTACT_US)){
            binding.container.setVisibility(View.VISIBLE);
            binding.email.setText(R.string.email);
            binding.number.setText(R.string.number);
            binding.title.setText("Contact us");
        }

        binding.back.setOnClickListener(v -> {
            onBackPressed();
        });



    }
}