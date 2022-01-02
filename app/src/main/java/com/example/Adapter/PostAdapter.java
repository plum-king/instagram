package com.example.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Model.Post;
import com.example.login.MainActivity;
import com.example.login.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends FirebaseRecyclerAdapter<Post, PostAdapter.PostViewHolder> {

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
//        holder.storyId.setText(model.getStoryId());
//
//        Glide.with(holder.storyImg.getContext())
//                .load(model.getStoryUrl())
//                .error(R.drawable.checked_story)
//                .into(holder.storyImg);
        holder.tv_userId.setText(model.getPublisher());
        holder.tv_description.setText(model.getPublisher());
        holder.tv_description.setText(model.getDescription());

        Picasso.get().load(model.getPostImg()).fit().into(holder.iv_post);
        Glide.with(holder.cv_user.getContext())
                .load(model.getUserImg()).
                error(R.drawable.checked_story).
                into(holder.cv_user);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,
                parent, false);
        return new PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        CircleImageView cv_user;
        ImageView iv_post;
        TextView tv_userId, tv_publisher, tv_description;

        public PostViewHolder(@NonNull View itemView) {

            super(itemView);

            cv_user = itemView.findViewById(R.id.userImg);
            iv_post = itemView.findViewById(R.id.postImg);
            tv_userId = itemView.findViewById(R.id.userId);
            tv_publisher = itemView.findViewById(R.id.publisher);
            tv_description = itemView.findViewById(R.id.description);
        }
//        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.decelerate_interpolator);
//        itemView.startAnimation(animation);
    }
}
