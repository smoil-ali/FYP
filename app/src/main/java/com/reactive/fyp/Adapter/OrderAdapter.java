package com.reactive.fyp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.reactive.fyp.Activities.OrderActivity;
import com.reactive.fyp.Interfaces.OrderAdapterListener;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.ItemOrderBinding;
import com.reactive.fyp.model.CartClass;
import com.reactive.fyp.model.ImageClass;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    final String TAG = OrderAdapter.class.getSimpleName();
    Context context;
    List<CartClass> list;
    OrderAdapterListener listener;

    public OrderAdapter(Context context, List<CartClass> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(OrderAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemOrderBinding binding = ItemOrderBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        CartClass cartClass = list.get(position);
        holder.bind(cartClass,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemOrderBinding binding;
        public ViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartClass cartClass, int position){
            binding.setData(cartClass);
            binding.executePendingBindings();

            binding.container.setOnClickListener(v -> {
                listener.OnClick(cartClass.getList());
            });
        }
    }
}
