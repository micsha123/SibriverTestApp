package com.sibriver.testapp.sibrivertestapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.bluelinelabs.logansquare.LoganSquare;
import com.sibriver.testapp.sibrivertestapp.data.Requests;
import com.sibriver.testapp.sibrivertestapp.model.Response;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");

        Bundle bundle = new Bundle();

        if (!TextUtils.isEmpty(url)) {
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);
            try {
                Response results = downloadData(url);

                if (null != results && results.response.size() > 0) {
                    bundle.putInt("result", results.response.size());
                    receiver.send(STATUS_FINISHED, bundle);
                }
            } catch (Exception e) {
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    private Response downloadData(String requestUrl) throws IOException, DownloadException {
        InputStream inputStream;
        HttpURLConnection urlConnection;

        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestProperty("Content-Type", "application/json");

        urlConnection.setRequestProperty("Accept", "application/json");

        urlConnection.setRequestMethod("POST");
        int statusCode = urlConnection.getResponseCode();

        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            Response dataFromInputStream = LoganSquare.parse(inputStream, Response.class);
            Requests requestsInstance = Requests.getInstance(this);
            requestsInstance.setRequests(dataFromInputStream.response);
//            requestsInstance.saveRequestsToDB();
            return dataFromInputStream;
        } else {
            throw new DownloadException("Error!");
        }
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

