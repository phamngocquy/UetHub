package com.uet.qpn.uethub.rclViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.uet.qpn.uethub.Activity_pdf_viewer;
import com.uet.qpn.uethub.DownloadService.DownloadService;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.entity.Subject;
import com.uet.qpn.uethub.entity.SubjectGroup;

import java.util.List;

public class RclExamViewAdapter extends RecyclerView.Adapter<RclExamViewAdapter.ViewHolder> {
    private List<SubjectGroup> subjectGroups;
    private Context context;

    public RclExamViewAdapter(List<SubjectGroup> subjectGroups, Context context) {
        this.subjectGroups = subjectGroups;
        this.context = context;
    }

    @Override
    public RclExamViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam, null);
        return new RclExamViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RclExamViewAdapter.ViewHolder holder, int position) {
        final SubjectGroup subjectGroup = subjectGroups.get(position);
        holder.txt_name_exam.setText(subjectGroup.getSubjectName());
        holder.txt_code_subject.setText(subjectGroup.getSubjectCode());
        holder.txt_msv.setText(subjectGroup.getMsv());
        holder.txt_candidate_number.setText(subjectGroup.getSbdUser());
        holder.date_exam.setText(subjectGroup.getExamDay());
        holder.room_exam.setText(subjectGroup.getExamRoom());
        holder.type_exam.setText(subjectGroup.getTypeExam());

    }

    @Override
    public int getItemCount() {
        return subjectGroups.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name_exam;
        TextView txt_code_subject;
        TextView txt_msv;

        //so bao danh
        TextView txt_candidate_number;

        TextView date_exam;
        TextView room_exam;
        TextView type_exam;

        ViewHolder(View itemView) {
            super(itemView);
            txt_name_exam = itemView.findViewById(R.id.txt_name_exam);
            txt_code_subject = itemView.findViewById(R.id.txt_code_subject);
            txt_msv = itemView.findViewById(R.id.txt_msv);
            txt_candidate_number = itemView.findViewById(R.id.txt_candidate_number);
            date_exam = itemView.findViewById(R.id.date_exam);
            room_exam = itemView.findViewById(R.id.room_exam);
            type_exam = itemView.findViewById(R.id.type_exam);
        }
    }


    public void upDateData(List<SubjectGroup> subjectGroups_in) {

        for (SubjectGroup entity : subjectGroups_in) {
            int i = 0;
            for (SubjectGroup entitySG : subjectGroups){
                if (entity.getSubjectCode().equals(entitySG.getSubjectCode())){
                    i++;
                    break;
                }
            }
            if(i == 0){
                subjectGroups.add(entity);
            }
        }

        notifyDataSetChanged();
    }

    public List<SubjectGroup> getSubjectGroups() {
        return subjectGroups;
    }
}
