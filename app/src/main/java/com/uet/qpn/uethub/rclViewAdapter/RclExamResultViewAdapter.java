package com.uet.qpn.uethub.rclViewAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.Subject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RclExamResultViewAdapter extends RecyclerView.Adapter<RclExamResultViewAdapter.ViewHolder> {

    private List<Subject> subjects;
    private Context context;

    public RclExamResultViewAdapter(List<Subject> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_exam, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        String txt_name_exam = subject.getName() + " - " + subject.getCode();
        holder.txt_name_exam_result.setText(txt_name_exam);
        holder.txt_time_public.setText(subject.getPublic_time());

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name_exam_result;
        TextView txt_time_public;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_name_exam_result = itemView.findViewById(R.id.txt_name_exam_result);
            txt_time_public = itemView.findViewById(R.id.txt_time_public);
        }
    }


    public void upDateData(List<Subject> subjects_) {
        for (Subject entity : subjects_) {
            if (!subjects.contains(entity)) subjects.add(entity);
        }

        notifyDataSetChanged();
    }
}
