package com.reactive.fyp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Adapter.OrderAdapter;
import com.reactive.fyp.Dialog.BottomOrderSheet;
import com.reactive.fyp.Interfaces.OrderAdapterListener;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.databinding.FragmentConfirmOrdreBinding;
import com.reactive.fyp.model.CartClass;
import com.reactive.fyp.model.ImageClass;

import java.util.ArrayList;
import java.util.List;


public class ConfirmOrdreFragment extends Fragment implements OrderAdapterListener {

    final String TAG = ConfirmOrdreFragment.class.getSimpleName();
    FragmentConfirmOrdreBinding binding;
    OrderAdapter adapter;
    List<CartClass> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_confirm_ordre,container,false);

        binding.setVisibility(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.progress.setVisibility(View.VISIBLE);
        binding.recycler.hasFixedSize();
        binding.recycler.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapter(getContext(),list);
        adapter.setListener(this);
        binding.recycler.setAdapter(adapter);

        binding.refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refresher.setRefreshing(false);
                getConfirmOrders();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getConfirmOrders();
    }

    void getConfirmOrders(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferencea = firebaseDatabase.getReference(Constants.Order);
        databaseReferencea.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderByChild("status").equalTo(true)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binding.progress.setVisibility(View.GONE);
                        if (snapshot.exists()){
                            list.clear();
                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                CartClass cartClass = snapshot1.getValue(CartClass.class);
                                cartClass.setId(snapshot1.getKey());
                                list.add(cartClass);
                                Log.i(TAG,cartClass.toString());
                            }
                            adapter.notifyDataSetChanged();
                            binding.setVisibility(true);
                        }else {
                            binding.setVisibility(false);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                        binding.setVisibility(false);
                        binding.progress.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void OnClick(List<ImageClass> list) {
        openDialog(list);
    }

    private void openDialog(List<ImageClass> list) {
        FragmentManager fm = getFragmentManager();
        BottomOrderSheet bottomOrderSheet = new BottomOrderSheet();
        bottomOrderSheet.setList(list);
        bottomOrderSheet.show(fm, "Bottom");
    }

}