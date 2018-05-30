package com.uet.qpn.uethub.DownloadService;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.uet.qpn.uethub.config.Configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {

    public static final int UPDATE_PROGRESS = 8344;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        String fileName = intent.getStringExtra("fileName");
        String url_pdf = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator;

        try {

            URL url = new URL(url_pdf.replaceAll(" ","%20"));
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            int fileLength = urlConnection.getContentLength();

            //download file
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            OutputStream outputStream = new FileOutputStream(path + File.separator + Configuration.STORE_FOLDER + File.separator + fileName);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = inputStream.read(data)) != -1) {
                total += count;

                //publishing the progress
                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                outputStream.write(data, 0, count);

            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);
        receiver.send(UPDATE_PROGRESS, resultData);

    }
}
