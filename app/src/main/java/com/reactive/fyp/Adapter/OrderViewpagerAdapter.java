package com.reactive.fyp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.reactive.fyp.Fragments.CartFragment;
import com.reactive.fyp.Fragments.ConfirmOrdreFragment;
import com.reactive.fyp.Fragments.NewFeedFragment;
import com.reactive.fyp.Fragments.PendingOrders;
import com.reactive.fyp.Fragments.ProfileFragment;

public class OrderViewpagerAdapter extends FragmentStateAdapter {

    private final int NUM_ITEMS = 2;

    public OrderViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PendingOrders();
            case 1:
                return new ConfirmOrdreFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
