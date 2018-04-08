package com.uet.qpn.uethub.rclViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.entity.NewsEntity;

import java.util.ArrayList;

public class RclNewsViewApdapter extends RecyclerView.Adapter<RclNewsViewApdapter.ViewHolder> {
    private ArrayList<NewsEntity> newsEntities;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsEntity entity = newsEntities.get(position);
        holder.txtDescription.setText(entity.getDescription());
        holder.txtPublicTime.setText(entity.getPublictime());
        holder.txtAuthor.setText(entity.getAuthor());
        holder.txtTitle.setText(entity.getTitle());
        holder.txtCategory.setText(entity.getCategories());
    }

    @Override
    public int getItemCount() {
        return newsEntities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtPublicTime;
        TextView txtCategory;
        TextView txtAuthor;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtCategory = itemView.findViewById(R.id.txtCategories);
            txtPublicTime = itemView.findViewById(R.id.txtPublicTime);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
