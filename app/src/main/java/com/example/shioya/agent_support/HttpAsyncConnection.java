package com.example.shioya.agent_support;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shioya on 2017/03/17.
 */

public class HttpAsyncConnection extends AsyncTask<String, Integer, String> {
    public interface AsyncTaskCallback {
        void postExecute(String res);
    }

    private final String TAG = "HttpAsyncConnection";
    Context context;
    private AsyncTaskCallback callback = null;
    int id = R.id.buttonSend;

    // URL
    URL url;
    HttpURLConnection httpurl;

    // PHPに送信するために必要
    PrintWriter pw;
    String jsonarray; // 送るJSON array

    BufferedReader r;


    // コンストラクタ
    public HttpAsyncConnection(Context context, String json, int id, AsyncTaskCallback _callback) {
        super();
        this.context = context;
        this.jsonarray = json;
        this.id = id;
        this.callback = _callback;
    }

    // 非同期処理の前の処理
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }

    // 実際に通信する部分
    @Override
    protected String doInBackground(String... params) {
        final int CONNECT_TIMEOUT = 10 * 1000;  // タイムアウト設定[ms]
        final String ENCORDING = "UTF-8";       // エンコード
        String result = null;

        try {
            // URLの指定 OPTUS IP 192.168.0.115
            switch (id) {
                case R.id.buttonSend:
                    url = new URL("http://192.168.0.115/Agent/point_receiver.php");
                    break;
                case R.id.buttonSynchro:
                    url = new URL("http://192.168.0.115/Agent/point_synchro.php");
            }

            // HTTP通信
            httpurl = (HttpURLConnection)url.openConnection();
            httpurl.setRequestMethod("POST");
            httpurl.setConnectTimeout(CONNECT_TIMEOUT);
            httpurl.setDoInput(true);
            httpurl.setDoOutput(true);
            httpurl.setUseCaches(false);
            httpurl.connect();


            switch (id) {
                // 送信用
                case R.id.buttonSend:
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(httpurl.getOutputStream(), ENCORDING)));
                    pw.write(jsonarray);
                    pw.close();

                    r = new BufferedReader(new InputStreamReader(httpurl.getInputStream()));
                    result = r.readLine();
                    System.out.println(result);
                    r.close();
                    break;
                // 受信用
                case R.id.buttonSynchro:
                    InputStream in = httpurl.getInputStream();
                    StringBuffer sb = new StringBuffer();
                    String st = "";
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, ENCORDING));
                    while ((st = br.readLine()) != null) {
                        sb.append(st);
                    }
                    try {
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    result = sb.toString();
                    System.out.println(result);
                    br.close();
                    break;
            }

            return result;



        } catch (MalformedURLException e) {
            e.printStackTrace();
            new AlertDialog.Builder(context)
                    .setTitle("異なるネットワーク")
                    .setMessage("指定されたネットワーク環境下で実行してください")
                    .setPositiveButton("OK", null)
                    .show();
            return "different network";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } finally {
            httpurl.disconnect();
        }
    }

    // 通信終了時
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.postExecute(result);
    }


}
