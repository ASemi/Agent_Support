package com.example.shioya.agent_support;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    Button btn_agent, btn_strategy, btn_subagent;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    BitmapFactory.Options opt = new BitmapFactory.Options();
    WebView webView;
    private ArrayList<String> agentlist = Agents.CreateFirst();
    Strategies strategies = new Strategies();
    private ArrayList<String> subagentlist = SubAgents.CreateFirst();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.backButton).setOnClickListener(this);

        btn_agent = (Button)findViewById(R.id.agentButton);
        btn_agent.setOnClickListener(this);
        btn_agent.setEnabled(false);
        btn_strategy = (Button)findViewById(R.id.strategyButton);
        btn_strategy.setOnClickListener(this);
        btn_strategy.setEnabled(true);
        btn_subagent = (Button)findViewById(R.id.subagentButton);
        btn_subagent.setOnClickListener(this);
        btn_subagent.setEnabled(true);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.backButton:
                finish();
                break;
            case R.id.agentButton:
                btn_agent.setEnabled(false);
                btn_strategy.setEnabled(true);
                btn_subagent.setEnabled(true);
                mRecyclerView.setAdapter(null);
                adapter = null;
                adapter = new MyAdapter(agentlist);
                mRecyclerView.setAdapter(adapter);
                break;
            case R.id.strategyButton:
                btn_agent.setEnabled(true);
                btn_strategy.setEnabled(false);
                btn_subagent.setEnabled(true);
                mRecyclerView.setAdapter(null);
                adapter = null;
                adapter = new MyAdapter(strategies.name);
                mRecyclerView.setAdapter(adapter);
                break;
            case R.id.subagentButton:
                btn_agent.setEnabled(true);
                btn_strategy.setEnabled(true);
                btn_subagent.setEnabled(false);
                mRecyclerView.setAdapter(null);
                adapter = null;
                adapter = new MyAdapter(subagentlist);
                mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView_card);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(agentlist);
        mRecyclerView.setAdapter(adapter);

        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true); // ピンチアウトの設定
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.loadUrl("file:///android_asset/"+agentlist.get(0)+".html");
    }

    @Override
    public void onStop() {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
            adapter = null;
        }
        if (webView != null) {
            webView = null;
        }
        super.onStop();
    }

    private class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        ArrayList<String> list;

        // コンストラクタ カード名のListを引数にとる
        public MyAdapter(ArrayList<String> Cards) {
            this.list = Cards;
        }

        // ViewHolderを返す
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.card_list, parent, false);
            return new ItemViewHolder(itemView);
        }

        // positionに対応する値をviewにいれる
        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            final String data = list.get(position);

            opt.inJustDecodeBounds = false;
            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(data, "drawable", getPackageName()), opt);

            holder.imageView.setImageBitmap(mBitmap);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.loadUrl("file:///android_asset/"+data+".html");
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ItemViewHolder(View v){
            super(v);
            imageView = (ImageView)v.findViewById(R.id.img_elem);
        }
    }
}
