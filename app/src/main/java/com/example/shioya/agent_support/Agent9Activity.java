package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class Agent9Activity extends Activity implements View.OnClickListener {

    ImageView[]agent_imglist = new ImageView[9];
    Bitmap[]bitmaps = new Bitmap[9];
    private static final int[]REQUEST_CODE = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int i;
    BitmapFactory.Options opt = new BitmapFactory.Options();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_nine);

        findViewById(R.id.backButton).setOnClickListener(this);

        for (i=0; i<9; i++) {
            String strNo = Integer.toString(i+1);
            agent_imglist[i]=(ImageView)findViewById(getResources().getIdentifier("agentImage"+strNo , "id" , getPackageName()));
            agent_imglist[i].setOnClickListener(this);
        }

        opt.inSampleSize = 2;
        opt.inJustDecodeBounds = false;
        for (i=0; i<9; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.agentback, opt);
            agent_imglist[i].setImageBitmap(bitmaps[i]);
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int j = 0;
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        /*
        switch (id) {
            case (R.id.agentImage1):
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.popselect:
                                toAgentSelect(0);
                                break;
                            case R.id.popdetail:
                                break;
                        }
                        return false;
                    }
                });

        }
        */
        if(id == R.id.backButton) {
            finish();
        } else {
            for (j = 0; j < 9; j++) {
                String strNo = Integer.toString(j + 1);
                if (id == getResources().getIdentifier("agentImage" + strNo, "id", getPackageName())) {
                    final int tmp = j;
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.popselect:
                                    //エージェント選択
                                    toAgentSelect(tmp);
                                    break;
                                case R.id.popdetail:
                                    //詳細
                                    break;
                            }
                            return false;

                        }
                    });
                    break;
                }
            }
        }

    }

    private void toAgentSelect(int j) {
        Intent intent = new Intent(this, AgentSelectActivity.class);
        startActivityForResult(intent, REQUEST_CODE[j]);
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        int j;
        Bundle resultBundle = data.getExtras();

        if (resultcode != RESULT_OK) {
            return;
        }

        if (!resultBundle.containsKey("result")) {
            return;
        }

        String result = resultBundle.getString("result");

        for(j=0; j<9; j++) {
            if(requestcode == REQUEST_CODE[j]) {
                // 9枚のどれから戻ってきたか
                bitmaps[j] = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(result, "drawable", getPackageName()), opt);
                agent_imglist[j].setImageBitmap(bitmaps[j]);
                return;
            }
        }
    }
}
