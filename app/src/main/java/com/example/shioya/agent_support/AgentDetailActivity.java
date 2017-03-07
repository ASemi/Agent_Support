package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

public class AgentDetailActivity extends Activity implements View.OnClickListener {

    ImageView imageView;
    WebView webView;
    String agent;
    Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_detail);

        findViewById(R.id.cancelButton).setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.agentDetailImage);
        webView = (WebView)findViewById(R.id.agentDetailWeb);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true); // ピンチアウトの設定
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.cancelButton:
                finish();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        agent = intent.getStringExtra("name");
        mBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(agent, "drawable", getPackageName()));
        imageView.setImageBitmap(mBitmap);

        webView.loadUrl("file:///android_asset/"+agent+".html");

    }

    @Override
    public void onPause() {
        if(webView != null) {
            webView = null;
        }
        if(!mBitmap.isRecycled()) {
            mBitmap.recycle();
        }

        super.onPause();
    }
}
