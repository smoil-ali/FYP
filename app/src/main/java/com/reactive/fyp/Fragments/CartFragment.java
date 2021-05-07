package com.reactive.fyp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.AbsSpinnerBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reactive.fyp.Activities.CheckOutActivity;
import com.reactive.fyp.Activities.OrderActivity;
import com.reactive.fyp.Adapter.CartAdapter;
import com.reactive.fyp.Adapter.OrderAdapter;
import com.reactive.fyp.Interfaces.CartListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.Utils.Helper;

import com.reactive.fyp.databinding.FragmentCartBinding;
import com.reactive.fyp.model.CartClass;
import com.reactive.fyp.model.ImageClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.LongFunction;

public class CartFragment extends Fragment implements CartListener {


    final String TAG = CartFragment.class.getSimpleName();
    FragmentCartBinding binding;
    CartAdapter adapter;
    List<ImageClass> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false);
        binding.setVisibility(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.recycler.setItemAnimator(null);


        binding.refresher.setOnRefreshListener(() -> getCartData());


        binding.orders.setOnClickListener(v -> {
            openOrderScreen();
        });

        binding.done.setOnClickListener(v -> {
            openScreen();
        });

        binding.back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });



        return binding.getRoot();
    }



    void getCartData(){
        String cartdata = Helper.getCartData(Objects.requireNonNull(getContext()));
        Log.i(TAG,cartdata);
        if (!cartdata.isEmpty() && !cartdata.equals("[]")){
            binding.setVisibility(true);
            list.clear();
            list.addAll(Helper.fromStringToList(cartdata));
            setupRecyclerView();
            Log.i(TAG,list.size()+"");
            binding.done.setEnabled(true);
        }else {
            binding.done.setEnabled(false);
            binding.setVisibility(false);
        }
        binding.refresher.setRefreshing(false);
    }

    void setupRecyclerView(){
        adapter = new CartAdapter(getContext(),list);
        binding.recycler.setAdapter(adapter);
        adapter.setListener(this::OnDelete);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"resume");
        getCartData();
    }

    @Override
    public void OnDelete(int pos) {
        String cartdata = Helper.getCartData(Objects.requireNonNull(getContext()));
        List<ImageClass> mlist = Helper.fromStringToList(cartdata);
        Log.i(TAG,mlist.size()+" before del");
        mlist.remove(pos);
        Log.i(TAG,mlist.size()+" after del");
        Helper.setCartData(Helper.fromListToString(mlist),getContext());
        list.remove(pos);
        if (list.size() > 0){
            adapter.notifyDataSetChanged();
        }else {
            binding.setVisibility(false);
        }
    }

    void openScreen(){
        CartClass cartClass = new CartClass();
        cartClass.setList(list);
        Intent intent = new Intent(getContext(), CheckOutActivity.class);
        intent.putExtra(Constants.PARAMS,cartClass);
        startActivity(intent);
    }

    void openOrderScreen(){
        Intent intent = new Intent(getContext(), OrderActivity.class);
        startActivity(intent);
    }
}