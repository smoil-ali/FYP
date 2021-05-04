package com.reactive.fyp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.TwitterAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.fyp.Activities.ImageViewActivity;
import com.reactive.fyp.R;
import com.reactive.fyp.Utils.Constants;
import com.reactive.fyp.databinding.ItemNewsfeedBinding;
import com.reactive.fyp.model.PostClass;
import com.reactive.fyp.model.ProfileModel;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    final String TAG = PostAdapter.class.getSimpleName();
    Context context;
    List<PostClass> list;

    public PostAdapter(Context context, List<PostClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_newsfeed,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.binding.container);
        PostClass postClass = list.get(position);
        getUserInfo(postClass.getPublisherId(),holder);
        Glide.with(context).load(postClass.getImage().getImage())
                .placeholder(R.drawable.picture)
                .into(holder.binding.postImage);
        holder.binding.date.setText(postClass.getTimestamp());
        holder.binding.postImage.setOnClickListener(v ->{
            Intent intent = new Intent(context, ImageViewActivity.class);
            intent.putExtra(Constants.PARAMS,list.get(position).getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemNewsfeedBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemNewsfeedBinding.bind(itemView);
        }
    }

    void getUserInfo(String id,ViewHolder holder){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USERS);
        databaseReference.child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ProfileModel model = snapshot.getValue(ProfileModel.class);
                        model.setId(snapshot.getKey());
                        Log.i(TAG,model.toString());
                        Glide.with(context).load(model.getImage())
                                .placeholder(R.drawable.user)
                                .into(holder.binding.user);
                        holder.binding.userName.setText(model.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                    }
                });
    }
}
