package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class AgentSelectActivity extends Activity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    BitmapFactory.Options opt = new BitmapFactory.Options();
    Agents agents = new Agents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_select);

        findViewById(R.id.backButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.backButton:
                Intent intent = new Intent();
                intent.putExtra("result", "agentback");
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_card);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AgentSelectActivity.MyAdapter(agents.name);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
            adapter = null;
        }
        super.onStop();
    }

    private class MyAdapter extends RecyclerView.Adapter<AgentSelectActivity.ItemViewHolder> {

        ArrayList<String> list;

        // コンストラクタ カード名のListを引数にとる
        public MyAdapter(ArrayList<String> Cards) {
            this.list = Cards;
        }

        // ViewHolderを返す
        @Override
        public AgentSelectActivity.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.card_list, parent, false);
            return new AgentSelectActivity.ItemViewHolder(itemView);
        }

        // positionに対応する値をviewにいれる
        @Override
        public void onBindViewHolder(AgentSelectActivity.ItemViewHolder holder, int position) {
            final String data = list.get(position);

            opt.inJustDecodeBounds = false;
            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(data, "drawable", getPackageName()), opt);

            holder.imageView.setImageBitmap(mBitmap);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("result", data);
                    setResult(RESULT_OK, intent);
                    finish();
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
