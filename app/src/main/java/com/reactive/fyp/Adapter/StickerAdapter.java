package com.reactive.fyp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.reactive.fyp.Interfaces.StickerListener;
import com.reactive.fyp.R;
import com.reactive.fyp.model.StickerModel;

import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    Context context;
    List<StickerModel> list;
    StickerListener listener;

    public void setListener(StickerListener listener) {
        this.listener = listener;
    }

    public StickerAdapter(Context context, List<StickerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sticker,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StickerModel model = list.get(position);
        holder.price.setText(model.getPrice());
        Glide.with(context).load(model.getImageUrl()).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnStickerClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price;
        ConstraintLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.shirtIcon);
            relativeLayout=itemView.findViewById(R.id.img_conationer);
            price = itemView.findViewById(R.id.price);
        }
    }
}
