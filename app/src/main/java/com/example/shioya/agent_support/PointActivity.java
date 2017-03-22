package com.example.shioya.agent_support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PointActivity extends Activity implements View.OnClickListener {

    ListView listView;
    ArrayList<PlayerPoint> players;
    PointAdapter adapter;

    // ネットワーク接続状態
    private enum NetworkStatus {
        OFF, WIFI, MOBILE, OTHER
    }

    NetworkStatus status = NetworkStatus.OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_list);

        findViewById(R.id.backButton).setOnClickListener(this);
        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonCopy).setOnClickListener(this);
        findViewById(R.id.buttonSend).setOnClickListener(this);

        //SharedPreferencesからの取得
        SharedPreferences sp = getApplicationContext().getSharedPreferences("PREF_KEY", Context.MODE_PRIVATE);
        String json = sp.getString("PLAYERS", "");
        Gson gson = new Gson();

        if (!TextUtils.isEmpty(json)) {
            players = gson.fromJson(json, new TypeToken<ArrayList<PlayerPoint>>(){}.getType());
        } else {
            players = new ArrayList<>();

            PlayerPoint playn = new PlayerPoint();
            playn.setName("塩谷");
            playn.setPoint(0);
            players.add(playn);
        }

        listView = (ListView)findViewById(R.id.pointlistView);
        adapter = new PointAdapter(this);
        adapter.setPlayerPoints(players);
        listView.setAdapter(adapter);

    }

    @Override
    public void onPause() {
        super.onPause();

        Gson gson = new Gson();
        String json = gson.toJson(players);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("PREF_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("PLAYERS", json);
        editor.apply();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.backButton:
                finish();
                break;
            case R.id.buttonAdd:
                addPlayer();
                break;
            case R.id.buttonCopy:
                copyPoint();
                break;
            case R.id.buttonSend:
                break;
        }
    }

    // プレイヤーをリストに追加
    private void addPlayer() {
        final EditText editView = new EditText(this);
        final PlayerPoint newplayer = new PlayerPoint();
        newplayer.setPoint(0);

        new AlertDialog.Builder(this).setTitle("名前").setView(editView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editView.getText().toString();
                newplayer.setName(name);
                players.add(newplayer);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("キャンセル", null).show();
    }

    // 得点状況をクリップボードにコピー
    private void copyPoint() {
        ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        StringBuilder buff = new StringBuilder();
        for(PlayerPoint player : players) {
            buff.append(player.name);
            buff.append(player.point);
            buff.append('\n');
        }
        ClipData cd = ClipData.newPlainText("POINT", buff.toString());
        cm.setPrimaryClip(cd);
        Toast.makeText(this, "クリップボードにコピーしました", Toast.LENGTH_SHORT).show();
    }

    // データの送信
    /*
    private void sendPoint() {
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        // 端末のネットワーク状態の確認
        if (nInfo != null && nInfo.isConnected()) {
            switch (nInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    status = NetworkStatus.WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    status = NetworkStatus.MOBILE;
                    break;
                default:
                    status = NetworkStatus.OTHER;
                    break;
            }
        }

        // 名前とポイントをJSONの形に
        Gson gson = new Gson();
        String json = gson.toJson(players);

        // ネットワーク接続状態に応じた処理
        if (status == NetworkStatus.WIFI) {
            HttpAsyncConnection connection = new HttpAsyncConnection(this, )
        }





    }
    */



    private class PointAdapter extends BaseAdapter {
        LayoutInflater layoutInflater = null;
        PlayerPoint player;
        ArrayList<PlayerPoint> playerPoints;
        Context context;

        public PointAdapter(Context context) {
            this.context = context;
            this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setPlayerPoints(ArrayList<PlayerPoint> playerPoints) {
            this.playerPoints = playerPoints;
        }

        @Override
        public int getCount() {
            return playerPoints.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.pointrow, parent, false);
            }
            player = playerPoints.get(position);

            TextView nameText = (TextView)convertView.findViewById(R.id.nameText);
            if (nameText != null) {
                nameText.setText(player.getName());
            }

            final TextView pointText = (TextView)convertView.findViewById(R.id.pointText);
            if (pointText != null) {
                pointText.setText(String.format("%d", player.getPoint()));
            }

            Button buttonPlus = (Button)convertView.findViewById(R.id.buttonPlus);
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerPoint p = playerPoints.get(position);
                    p.point++;
                    pointText.setText(String.format("%d", p.getPoint()));
                }
            });

            Button buttonMinus = (Button)convertView.findViewById(R.id.buttonMinus);
            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayerPoint p = playerPoints.get(position);
                    p.point--;
                    pointText.setText(String.format("%d", p.getPoint()));
                }
            });

            Button buttonDelete = (Button)convertView.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerPoints.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;

        }
    }

}


