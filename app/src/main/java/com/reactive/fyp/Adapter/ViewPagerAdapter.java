package com.reactive.fyp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.reactive.fyp.Fragments.CartFragment;
import com.reactive.fyp.Fragments.NewFeedFragment;
import com.reactive.fyp.Fragments.ProfileFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final int NUM_ITEMS = 3;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NewFeedFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new CartFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
