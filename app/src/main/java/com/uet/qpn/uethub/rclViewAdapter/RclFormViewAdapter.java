package com.uet.qpn.uethub.rclViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.uet.qpn.uethub.Activity_pdf_viewer;
import com.uet.qpn.uethub.DownloadService.DownloadService;
import com.uet.qpn.uethub.Helper;
import com.uet.qpn.uethub.R;
import com.uet.qpn.uethub.entity.Form;
import com.uet.qpn.uethub.entity.SubjectGroup;

import java.util.ArrayList;

public class RclFormViewAdapter extends RecyclerView.Adapter<RclFormViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Form> formList;

    public RclFormViewAdapter(Context context, ArrayList<Form> formList) {
        this.context = context;
        this.formList = formList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_form, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Form form = formList.get(position);
        holder.txtFormName.setText(form.getName());
        holder.txtTimeCreate.setText(form.getCreateTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = form.getUrl().substring(form.getUrl().lastIndexOf("/") + 1, form.getUrl().length());

                if (Helper.checkFileExist(fileName)) {
                    // show paf
                    // Log.d("check", "true");
                    Intent intent = new Intent(context, Activity_pdf_viewer.class);
                    intent.putExtra("filepath", fileName);
                    intent.putExtra("filename", form.getName());
                    context.startActivity(intent);
                } else {
                    // if file not exist
                    holder.number_progress_bar.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(context, DownloadService.class);
                    //Log.d("url_pdf", subject.getUrl());
                    intent.putExtra("url", form.getUrl());
                    intent.putExtra("receiver", new RclFormViewAdapter.DownloadReceiver(new Handler(), holder.number_progress_bar));
                    intent.putExtra("fileName", fileName);
                    context.startService(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return formList.size();
    }


    public void updateData(ArrayList<Form> formList_) {
        for (Form form : formList_) {
            int i = 0;
            for (Form form_tmp : formList){
                if (form.getLocal_url().equals(form_tmp.getLocal_url())){
                    i++;
                    break;
                }
            }
            if(i == 0){
                formList.add(form);
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFormName;
        TextView txtTimeCreate;
        NumberProgressBar number_progress_bar;

        ViewHolder(View itemView) {
            super(itemView);
            txtFormName = itemView.findViewById(R.id.txtFormName);
            txtTimeCreate = itemView.findViewById(R.id.txtTimeCreate);
            number_progress_bar = itemView.findViewById(R.id.number_progress_bar);
        }

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


}
