package com.example.thoughtsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ThoughtsAdapter extends RecyclerView.Adapter<ThoughtsAdapter.ViewHolder> {
    private List<Thoughts> thoughtsList;
    private Context context;
    private OnLikeImageClickListener onImageClick;

    public ThoughtsAdapter(Context context , OnLikeImageClickListener onImageClick) {
        this.context = context;
        this.onImageClick = onImageClick ;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.thought_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
         Thoughts thought = thoughtsList.get(position);
        holder.userName.setText(thought.getUsername());
        holder.thoughtTxt.setText(thought.getThoughtsText());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , h:mm a");
        String date = dateFormat.format(thought.getTimestamp());
        holder.timeStamp.setText(date);
        holder.numLikes.setText(thought.getNumLikes().toString());
        holder.rvLikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick.onImageClick(thoughtsList , position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thoughtsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView timeStamp;
        TextView thoughtTxt;
        TextView numLikes;
        ImageView rvLikeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.rvUserName);
            timeStamp = itemView.findViewById(R.id.rvTimeStamp);
            thoughtTxt = itemView.findViewById(R.id.rvThoughtTxt);
            numLikes = itemView.findViewById(R.id.rvNumLikesLabel);
            rvLikeImage = itemView.findViewById(R.id.rvLikeImage);


        }


    }

    public interface OnLikeImageClickListener {
        void onImageClick(List<Thoughts> list ,int position);
    }

    public void updateList(List<Thoughts> list ) {
        thoughtsList = new ArrayList<>(list);
        notifyDataSetChanged();

    }
}
