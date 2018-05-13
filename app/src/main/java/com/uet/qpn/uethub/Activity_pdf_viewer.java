package com.uet.qpn.uethub;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.uet.qpn.uethub.config.Configuration;

import java.io.File;
import java.net.URI;

public class Activity_pdf_viewer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        Toolbar toolbar = findViewById(R.id.readerPdf_toolbar);

        PDFView pdfView = findViewById(R.id.pdfView);
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator;
        String filepath = getIntent().getStringExtra("filepath");
        String filename = getIntent().getStringExtra("filename");
        toolbar.setTitle(filename);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        pdfView.fromFile(new File(path + Configuration.STORE_FOLDER + File.separator + filepath)).load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.open_outer_nemu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_outer_open:
                open_folder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void open_folder() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + File.separator + Configuration.STORE_FOLDER + File.separator);
        intent.setDataAndType(uri, "resource/folder");
        startActivity(Intent.createChooser(intent, "Open"));
    }
}
