package com.example.thoughtsapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thoughtsapp.Model.Comments;
import com.example.thoughtsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private List<Comments> commentsList;
    private Context context;

    public CommentsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.comment_rv_item, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {

        Comments comment = commentsList.get(position);
        holder.commentTxt.setText(comment.getCommentTxt());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d , h:mm a");
        String date = dateFormat.format(comment.getTimestamp());
        Log.d("JJJJJ", date);

        holder.timeStamp.setText(date);
        holder.userName.setText(comment.getUsername());


    }

    @Override
    public int getItemCount() {
        if (commentsList==null)
        return 0;
        return commentsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView timeStamp;
        TextView commentTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.commentListName);
            timeStamp = itemView.findViewById(R.id.commentListTimestamp);
            commentTxt = itemView.findViewById(R.id.commentListTxt);
        }
    }


    public void updateList(List<Comments> list) {
        commentsList = new ArrayList<>(list);
        notifyDataSetChanged();

    }
}
