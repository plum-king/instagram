package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.login.R;
import com.example.Model.Story;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends FirebaseRecyclerAdapter<Story, StoryAdapter.storyViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StoryAdapter(@NonNull FirebaseRecyclerOptions<Story> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull storyViewHolder holder, int position, @NonNull Story model) {
        holder.storyId.setText(model.getStoryId());

        Glide.with(holder.storyImg.getContext())
                .load(model.getStoryUrl())
                .error(R.drawable.checked_story)
                .into(holder.storyImg);
    }

    @NonNull
    @Override
    public storyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story,
                parent, false);
        return new storyViewHolder(view);
    }

    class storyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView storyImg;
        TextView storyId;

        public storyViewHolder(@NonNull View itemView) {
            super(itemView);

            storyImg = itemView.findViewById(R.id.storyImg);
            storyId = itemView.findViewById(R.id.storyId);
        }
    }
}
