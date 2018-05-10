package com.uet.qpn.uethub.rclViewAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.uet.qpn.uethub.entity.NewsEntity;
import com.uet.qpn.uethub.entity.Subject;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import is.arontibo.library.ElasticDownloadView;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Subject subject = subjects.get(position);
        holder.txt_name_exam_result.setText(subject.getName());
        holder.txt_time_public.setText(subject.getPublic_time());
        holder.txt_code_subject.setText(subject.getCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = subject.getUrl().substring(subject.getUrl().lastIndexOf("/") + 1, subject.getUrl().length());


                //if file exist
                if (Helper.checkFileExist(fileName)) {
                    // show paf
                   // Log.d("check", "true");
                    Intent intent = new Intent(context, Activity_pdf_viewer.class);
                    intent.putExtra("filepath", fileName);
                    context.startActivity(intent);
                } else {
                    // if file not exist
                    holder.numberProgressBar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(context, DownloadService.class);
                    //Log.d("url_pdf", subject.getUrl());

                    intent.putExtra("url", subject.getUrl());
                    intent.putExtra("receiver", new DownloadReceiver(new Handler(), holder.numberProgressBar));
                    intent.putExtra("fileName", fileName);
                    context.startService(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name_exam_result;
        TextView txt_time_public;
        TextView txt_code_subject;
        NumberProgressBar numberProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_name_exam_result = itemView.findViewById(R.id.txt_name_exam_result);
            txt_time_public = itemView.findViewById(R.id.txt_time_public);
            txt_code_subject = itemView.findViewById(R.id.txt_code_subject);
            numberProgressBar = itemView.findViewById(R.id.number_progress_bar);
        }
    }


    public void upDateData(List<Subject> subjects_) {
        for (Subject entity : subjects_) {
            if (!subjects.contains(entity)) subjects.add(entity);
        }

        notifyDataSetChanged();
    }

    private class DownloadReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        private NumberProgressBar numberProgressBar;

        public DownloadReceiver(Handler handler, NumberProgressBar numberProgressBar) {
            super(handler);
            this.numberProgressBar = numberProgressBar;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                int progress = resultData.getInt("progress");
                numberProgressBar.setProgress(progress);
                if (progress == 100) {
                    numberProgressBar.setVisibility(View.GONE);
                }

            }
        }
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
