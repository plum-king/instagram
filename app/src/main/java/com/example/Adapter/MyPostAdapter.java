package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Model.MyPost;
import com.example.login.R;



import java.util.List;



public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder> {

    private Context context;
    private List<MyPost> myPosts;

    public MyPostAdapter(Context context, List<MyPost> myPosts) {
        this.context = context;
        this.myPosts = myPosts;
    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos, parent, false);

        return new MyPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {
        int safePosition = holder.getAdapterPosition();
        MyPost post = myPosts.get(safePosition);

        holder.publisher = post.getPublisher();
        Glide.with(holder.post_img.getContext())
                .load(post.getpostImg())
                .into(holder.post_img);
    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }


    public class MyPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView post_img;
        String publisher;

        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            post_img = itemView.findViewById(R.id.post_img);
        }
    }

}

