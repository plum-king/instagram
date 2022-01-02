//package com.example.login;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.storage.FirebaseStorage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class UploadedImageAdapter extends RecyclerView.Adapter<UploadedImageAdapter.ViewHolder>
//{
//    private List<PostModel> imageList = new ArrayList<>();
//    private List<String> uidList = new ArrayList<>();
//    private FirebaseStorage storage;
//    private Context context;
//
//    public UploadedImageAdapter(){}
//    public UploadedImageAdapter(List<PostModel> imageList, List<String> uidList)
//    {
//        this.imageList = imageList;
//        this.uidList = uidList;
//        storage = FirebaseStorage.getInstance();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//    {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.activity_main,parent,false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
//    {
//        holder.textViewUser.setText(imageList.get(position).getUserId());
//        holder.textViewCont.setText(imageList.get(position).getDescription());
//        holder.imageView.setImageResource();
//
//        context = holder.itemView.getContext();
//        String url = imageList.get(position).getImageUrl();
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.g3)
//                .into(holder.imageView);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return imageList.size();
//    }
//
//    //ViewHolder 클래스
//    class ViewHolder extends RecyclerView.ViewHolder
//    {
//        public TextView textViewUser;
//        public TextView textViewCont;
//        public ImageView imageView;
//
//        public ViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//            textViewUser = itemView.findViewById(R.id.ID);
//            textViewCont = itemView.findViewById(R.id.showContents);
//            imageView = itemView.findViewById(R.id.showImage);
//        }
//    }
//}
