package com.reactive.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reactive.fyp.Adapter.OrderViewpagerAdapter;
import com.reactive.fyp.Adapter.ViewPagerAdapter;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    final String TAG = OrderActivity.class.getSimpleName();
    ActivityOrderBinding binding;
    OrderViewpagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_order);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null)
            binding.bottomNavigation.setSelectedItemId(R.id.pending);


        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_color));

        adapter = new OrderViewpagerAdapter(this);
        binding.pager.setAdapter(adapter);
        binding.pager.setUserInputEnabled(false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.pending:
                binding.pager.setCurrentItem(0);
                return true;
            case R.id.confirm:
                binding.pager.setCurrentItem(1);
                return true;
        }
        return true;
    }
}