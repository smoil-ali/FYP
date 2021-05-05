package com.reactive.fyp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.reactive.fyp.Interfaces.ShirtListener;
import com.reactive.fyp.R;
import com.reactive.fyp.model.ProductModel;
import com.reactive.fyp.model.ProfileModel;

import java.util.List;

public class ShirtAdapter extends RecyclerView.Adapter<ShirtAdapter.ViewHolder> {
    Context context;
    List<ProductModel> list;
    ShirtListener listener;

    public void setListener(ShirtListener listener) {
        this.listener = listener;
    }

    public ShirtAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_shirt,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        Glide.with(context).load(model.getImage())
                .placeholder(R.drawable.picture)
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(v ->
                listener.OnShirtClick(model));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.shirtIcon);
            relativeLayout=itemView.findViewById(R.id.img_conationer);
        }
    }
}
