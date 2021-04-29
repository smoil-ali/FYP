package com.reactive.fyp.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.reactive.fyp.Interfaces.TextClickListener;
import com.reactive.fyp.R;

import java.nio.channels.ReadableByteChannel;
import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    Context context;
    List<String> list;
    List<Integer> fontlist;
    TextClickListener listener;

    public TextAdapter(Context context, List<String> list,List<Integer> fontlist) {
        this.context = context;
        this.list = list;
        this.fontlist = fontlist;
    }

    public void setListener(TextClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_text,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.textView.setTypeface(ResourcesCompat.getFont(context,fontlist.get(position)));
        holder.relativeLayout.setOnClickListener(v -> {
            listener.onTextClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            relativeLayout = itemView.findViewById(R.id.txt_conationer);
        }
    }
}
