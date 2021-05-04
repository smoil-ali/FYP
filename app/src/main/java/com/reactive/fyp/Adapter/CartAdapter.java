package com.reactive.fyp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.reactive.fyp.Interfaces.CartListener;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.ItemCartBinding;
import com.reactive.fyp.model.ImageClass;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    final String TAG = CartAdapter.class.getSimpleName();
    Context context;
    List<ImageClass> list;
    CartListener listener;

    public CartAdapter(Context context, List<ImageClass> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(CartListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemCartBinding binding = ItemCartBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        ImageClass imageClass = list.get(position);
        holder.bind(imageClass,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCartBinding binding;
        public ViewHolder(@NonNull ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.executePendingBindings();
        }

        public void bind(ImageClass imageClass, int position){
            binding.setData(imageClass);
            binding.executePendingBindings();


            Glide.with(context).load(imageClass.getImage())
                    .placeholder(R.drawable.picture)
                    .into(binding.image);
            binding.trash.setOnClickListener(v -> {
                listener.OnDelete(position);
            });
            binding.numberPicker.setMin(1);
            binding.numberPicker.setValueChangedListener(new ValueChangedListener() {
                @Override
                public void valueChanged(int value, ActionEnum action) {

                    int price = Integer.parseInt(imageClass.getActualPrice());
                    price = price * value;
                    imageClass.setPrice(price+"");
                    imageClass.setQty(value+"");
                }
            });
        }
    }
}
