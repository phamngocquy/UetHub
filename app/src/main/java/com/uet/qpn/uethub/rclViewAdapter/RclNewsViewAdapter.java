package com.uet.qpn.uethub.rclViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.ReadNewsActivity;
import com.uet.qpn.uethub.entity.NewsEntity;

import java.util.ArrayList;

import io.realm.Realm;

public class RclNewsViewAdapter extends RecyclerView.Adapter<RclNewsViewAdapter.ViewHolder> {
    private ArrayList<NewsEntity> newsEntities;
    private Context context;

    public RclNewsViewAdapter(ArrayList<NewsEntity> newsEntities, Context context) {
        this.newsEntities = newsEntities;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_layout, null);
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
                if (Helper.isOnlineABoolean(context)) {
                    NewsEntity newsEntity = new NewsEntity();
                    newsEntity.setTitle(entity.getTitle());
                    newsEntity.setDescription(entity.getDescription());
                    newsEntity.setCategories(entity.getCategories());
                    newsEntity.setPublictime(entity.getPublictime());
                    newsEntity.setAuthor(entity.getAuthor());
                    newsEntity.setUrl(entity.getUrl());
                    newsEntity.setNewsName(entity.getNewsName());
                    Intent intent = new Intent(context, ReadNewsActivity.class);
                    intent.putExtra("news", newsEntity);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No internet connecting", Toast.LENGTH_SHORT).show();
                }
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
        for (int k = 0; k < list.size(); k++) {
            int i = 0;
            NewsEntity entity = list.get(k);
            for (int j = 0; j < newsEntities.size(); j++) {
                NewsEntity entity_tmp = newsEntities.get(j);
                if (entity.getUrl() != null && entity_tmp.getUrl() != null) {
                    if (entity.getUrl().equals(entity_tmp.getUrl())) {
                        i++;
                        break;
                    }
                }
            }
            if (i != 0) {
                list.remove(k);
                k--;
            }
        }
        list.addAll(newsEntities);
        newsEntities = list;
        notifyDataSetChanged();
    }

    public ArrayList<NewsEntity> getNewsEntities() {
        return newsEntities;
    }
}
