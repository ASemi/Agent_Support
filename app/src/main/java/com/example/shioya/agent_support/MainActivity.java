package com.example.shioya.agent_support;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;

// card_listアクティビティにこれらは移植する
// MainActivityは起動画面
public class MainActivity extends Activity implements View.OnClickListener {

    ImageButton imageButton1;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton1 = (ImageButton)findViewById(R.id.detectiveButton);
        imageButton1.setOnClickListener(this);
        imageButton1.setImageResource(R.drawable.nightmare);

        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true); // ピンチアウトの設定

    }

    // ImageButton配列とHTMLリソースの配列を用意してfor文の中でifする感じにする
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.detectiveButton:
                webView.loadUrl("file:///android_asset/nightmare.html");
                break;
        }
    }
}
