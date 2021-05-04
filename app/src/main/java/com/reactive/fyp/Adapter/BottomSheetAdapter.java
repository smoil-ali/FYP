package com.reactive.fyp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.reactive.fyp.R;
import com.reactive.fyp.databinding.ItemOrderBottomSheetBinding;
import com.reactive.fyp.model.ImageClass;

import java.util.List;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {
    final String TAG = BottomSheetAdapter.class.getSimpleName();
    Context context;
    List<ImageClass> list;

    public BottomSheetAdapter(Context context, List<ImageClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemOrderBottomSheetBinding bottomSheetBinding = ItemOrderBottomSheetBinding
                .inflate(layoutInflater,parent,false);
        return new ViewHolder(bottomSheetBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBottomSheetBinding binding;
        public ViewHolder(ItemOrderBottomSheetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ImageClass imageClass){
            binding.setData(imageClass);
            Glide.with(context)
                    .load(imageClass.getImage())
                    .placeholder(R.drawable.picture)
                    .into(binding.image);
        }
    }
}
