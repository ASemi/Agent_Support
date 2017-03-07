package com.example.shioya.agent_support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Agent9Activity extends Activity implements View.OnClickListener, SensorEventListener {

    ImageView[]agent_imglist = new ImageView[9];
    Bitmap[]bitmaps = new Bitmap[9];
    String[]agent_selected = new String[9];
    LinearLayout[]agent_layout = new LinearLayout[9];
    AgentSide[]agent_side = new AgentSide[9];

    private static final int[]REQUEST_CODE = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int i;
    BitmapFactory.Options opt = new BitmapFactory.Options();

    // 傾き取得関連
    SensorManager sensorManager;
    float[] rotationMatrix = new float[9];
    float[] gravity = new float[3];
    float[] geomagnetic = new float[3];
    float[] attitude = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_nine);

        findViewById(R.id.backButton).setOnClickListener(this);

        for (i=0; i<9; i++) {
            agent_side[i] = new AgentSide();

            String strNo = Integer.toString(i+1);
            agent_layout[i] = (LinearLayout)findViewById(getResources().getIdentifier("agentLayout"+strNo, "id", getPackageName()));

            agent_imglist[i]=(ImageView)findViewById(getResources().getIdentifier("agentImage"+strNo , "id" , getPackageName()));
            agent_imglist[i].setOnClickListener(this);
        }

        opt.inSampleSize = 2;
        opt.inJustDecodeBounds = false;
        for (i=0; i<9; i++) {
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.agentback, opt);
            agent_imglist[i].setImageBitmap(bitmaps[i]);
        }

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        int j;
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int j;
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
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
                                    toAgentDetail(tmp);
                                    break;
                                case R.id.popprove:
                                    //証明色設定
                                    setProveColor(tmp);
                                    break;
                            }
                            return false;

                        }
                    });

                }
            }
        }

    }

    // エージェント選択アクティビティへの遷移
    private void toAgentSelect(int j) {
        Intent intent = new Intent(this, AgentSelectActivity.class);
        startActivityForResult(intent, REQUEST_CODE[j]);
    }

    // エージェント詳細アクティビティへの遷移
    private void toAgentDetail(int j) {
        if (agent_selected[j] == null ) {
            return;
        }
        final String agent = agent_selected[j];
        Intent intent = new Intent(this, AgentDetailActivity.class);
        intent.putExtra("name", agent);
        startActivity(intent);

    }

    // 証明色設定
    // Alert Dialogの設定は以下を参考に
    // http://qiita.com/suzukihr/items/8973527ebb8bb35f6bb8

    private void setProveColor(int j) {
        final String[] side_id = {"赤", "青", "ＭＯＦ"}; // AlertDialogに表示する文字
        int defaultItem = 0; // デフォルトでチェックされているアイテム
        final int tmp = j;
        final List<Integer> checkedItems = new ArrayList<>();
        checkedItems.add(defaultItem);

        new AlertDialog.Builder(this).setTitle("陣営").setSingleChoiceItems(side_id, defaultItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedItems.clear();
                checkedItems.add(which);
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!checkedItems.isEmpty()) {
                    switch (checkedItems.get(0)) {
                        case 0:
                            agent_side[tmp].side = Color.RED;
                            break;
                        case 1:
                            agent_side[tmp].side = Color.BLUE;
                            break;
                        case 2:
                            agent_side[tmp].side = Color.YELLOW;
                            break;
                    }
                }
            }
        }).setNegativeButton("キャンセル", null).show();

        return;
    }


    // 選択アクティビティから戻ってきたときに呼ばれる関数
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        int j;


        if(resultcode != RESULT_OK) {
            return;
        }

        Bundle resultBundle = data.getExtras();

        if (!resultBundle.containsKey("result")) {
            return;
        }

        String result = resultBundle.getString("result");

        for(j=0; j<9; j++) {
            if(requestcode == REQUEST_CODE[j]) {
                // 9枚のどれから戻ってきたか
                bitmaps[j] = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(result, "drawable", getPackageName()), opt);
                agent_imglist[j].setImageBitmap(bitmaps[j]);
                agent_selected[j] = result;
                return;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
        int j;

        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomagnetic = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                gravity = event.values.clone();
                break;
        }

        if(geomagnetic != null && gravity != null) {
            sensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic);
            sensorManager.getOrientation(rotationMatrix, attitude);

            if (Math.toDegrees(attitude[2]) < -50.0) {
                for (j=0; j<9; j++) {
                    agent_layout[j].setBackgroundColor(agent_side[j].side);
                }
            } else {
                for (j=0; j<9; j++) {
                    agent_layout[j].setBackgroundColor(Color.BLACK);
                }
            }
        }

        return;

    }
}
