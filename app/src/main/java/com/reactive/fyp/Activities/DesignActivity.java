package com.reactive.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.reactive.fyp.Fragments.ShirtsFragment;
import com.reactive.fyp.Fragments.StickerFragment;
import com.reactive.fyp.R;

public class DesignActivity extends AppCompatActivity implements View.OnClickListener  {

    public ImageView home_shirt,home_sticker;
    RelativeLayout shirt,sticker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        home_shirt=findViewById(R.id.home_shirt);
        home_sticker=findViewById(R.id.home_sticker);
        shirt=findViewById(R.id.shirtWrapper);
        sticker=findViewById(R.id.stickerWrapper);
        shirt.setOnClickListener(this);
        sticker.setOnClickListener(this);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shirtContainer, new ShirtsFragment())
                    .commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shirtWrapper:
                openWindow(new ShirtsFragment());
                break;
            case R.id.stickerWrapper:
                openWindow(new StickerFragment());
                break;
        }
    }

    void openWindow(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.shirtContainer,fragment)
                .commit();
    }
}