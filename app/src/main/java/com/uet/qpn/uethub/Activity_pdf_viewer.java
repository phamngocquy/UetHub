package com.uet.qpn.uethub;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.uet.qpn.uethub.config.Configuration;

import java.io.File;

public class Activity_pdf_viewer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        PDFView pdfView = findViewById(R.id.pdfView);


        String path = Environment.getExternalStorageDirectory().getPath() + File.separator;
        String filepath = getIntent().getStringExtra("filepath");
        //Log.d("filepath", path + Configuration.STORE_FOLDER + File.separator + filepath);
        pdfView.fromFile(new File(path + Configuration.STORE_FOLDER + File.separator + filepath)).load();
    }
}
