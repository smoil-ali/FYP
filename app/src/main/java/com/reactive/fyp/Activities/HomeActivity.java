package com.reactive.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reactive.fyp.Adapter.ViewPagerAdapter;
import com.reactive.fyp.Fragments.CartFragment;
import com.reactive.fyp.Fragments.NewFeedFragment;
import com.reactive.fyp.Fragments.ProfileFragment;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final String TAG = HomeActivity.class.getSimpleName();
    ActivityHomeBinding binding;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_color));


        if (savedInstanceState == null)
            binding.bottomNavigation.setSelectedItemId(R.id.news_feed);

        viewPagerAdapter = new ViewPagerAdapter(this);
        binding.pager.setAdapter(viewPagerAdapter);
        binding.pager.setUserInputEnabled(false);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.news_feed:
                binding.pager.setCurrentItem(0);
                return true;
            case R.id.profile:
                binding.pager.setCurrentItem(1);
                return true;
            case R.id.cart:
                binding.pager.setCurrentItem(2);
                return true;
            case R.id.list_menu:
                binding.pager.setCurrentItem(3);
                return true;
        }
        return true;
    }
}