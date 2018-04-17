package com.uet.qpn.uethub.rclViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.ReadNewsActivity;
import com.uet.qpn.uethub.entity.NewsEntity;

import java.util.ArrayList;

public class RclNewsViewAdapter extends RecyclerView.Adapter<RclNewsViewAdapter.ViewHolder> {
    private ArrayList<NewsEntity> newsEntities;
    private Context context;


    public RclNewsViewAdapter() {

    }

    public RclNewsViewAdapter(ArrayList<NewsEntity> newsEntities, Context context) {
        this.newsEntities = newsEntities;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsEntity entity = newsEntities.get(position);
        holder.txtDescription.setText(entity.getDescription());
        holder.txtPublicTime.setText(entity.getPublictime());
        holder.txtAuthor.setText(entity.getAuthor());
        holder.txtTitle.setText(entity.getTitle());
        holder.txtCategory.setText(entity.getCategories());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadNewsActivity.class);
                intent.putExtra("news", entity);
                context.startActivity(intent);
            }
        });
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

    public void addItem(NewsEntity newsEntity) {
        newsEntities.add(newsEntity);
        notifyItemInserted(newsEntities.size() - 1);
    }

    public void upDateData(ArrayList<NewsEntity> list) {
        for (NewsEntity entity : list) {
            if (!newsEntities.contains(entity)) newsEntities.add(entity);
        }

        notifyDataSetChanged();
    }

    public ArrayList<NewsEntity> getNewsEntities() {
        return newsEntities;
    }
}