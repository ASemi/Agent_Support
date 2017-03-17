package com.example.shioya.agent_support;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shioya on 2017/03/17.
 */
/*
public class HttpAsyncConnection extends AsyncTask<String, Integer, JSONObject> {
    public interface AsyncTaskCallback {
        void postExecute();
    }

    private final String TAG = "HttpAsyncConnection";
    Context context;
    private AsyncTaskCallback callback = null;

    // URL
    URL url;
    HttpURLConnection httpurl;


    // コンストラクタ
    public HttpAsyncConnection(Context context, AsyncTaskCallback _callback) {
        super();
        this.context = context;
        this.callback = _callback;
    }

    // 非同期処理の前の処理
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 実際に通信する部分
    @Override
    protected JSONObject doInBackground(String... uri) {
        url = new URL("http://192.168.0.115/Agent/point_receiver.php");
    }
}
*/