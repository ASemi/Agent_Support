package com.example.shioya.agent_support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.HashMap;

public class PointActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    ListView listView;
    ArrayList<PlayerPoint> players;
    PointAdapter adapter;
    private boolean SuperUser = true;

    TextToSpeech tts;
    private static final String TAG = "PointTTS";

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
        findViewById(R.id.buttonZero).setOnClickListener(this);
        findViewById(R.id.buttonSend).setOnClickListener(this);
        findViewById(R.id.buttonSynchro).setOnClickListener(this);

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

        tts = new TextToSpeech(this, this);

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
            case R.id.buttonZero:
                toReset();
                break;
            case R.id.buttonCopy:
                copyPoint();
                break;
            case R.id.buttonSend:
                if (SuperUser == false) {
                    new AlertDialog.Builder(this)
                            .setTitle("送信")
                            .setMessage("権限がありません")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    sendPoint(R.id.buttonSend);
                }
                break;
            case R.id.buttonSynchro:
                sendPoint(R.id.buttonSynchro);
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

    private void toReset() {
        new AlertDialog.Builder(this)
                .setTitle("点数リセット")
                .setMessage("点数をリセットします。よろしいですか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(PlayerPoint p : players) {
                            p.point = 0;
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }

    // データの送信
    // idにはボタンのIDを指定
    // 「送信」ボタン：現在のポイントデータをサーバに送信
    // 「同期」ボタン：サーバからポイントデータを取得
    private void sendPoint(final int id) {
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        String json = null;

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

        if (id == R.id.buttonSend) {
            // 名前とポイントをJSONの形に
            Gson gson = new Gson();
            json = gson.toJson(players);
        }


        // ネットワーク接続状態に応じた処理
        if (status == NetworkStatus.WIFI) {

            HttpAsyncConnection connection = new HttpAsyncConnection(this, json, id, new HttpAsyncConnection.AsyncTaskCallback() {

                @Override
                public void postExecute(String res) {
                    switch (id) {
                        case R.id.buttonSend:
                            Toast.makeText(PointActivity.this, "送信完了", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.buttonSynchro:
                            final String json_sync = res;
                            new AlertDialog.Builder(PointActivity.this)
                                    .setTitle("同期")
                                    .setMessage("プレイヤーと点数の同期を行います")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Gson gson = new Gson();
                                            players = gson.fromJson(json_sync, new TypeToken<ArrayList<PlayerPoint>>(){}.getType());
                                            listView.setAdapter(null);
                                            adapter = null;
                                            adapter = new PointAdapter(PointActivity.this);
                                            adapter.setPlayerPoints(players);
                                            listView.setAdapter(adapter);
                                            Toast.makeText(PointActivity.this, "同期完了", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("キャンセル", null)
                                    .show();


                            break;
                    }
                }
            });
            connection.execute();


        } else {
            new AlertDialog.Builder(PointActivity.this)
                    .setTitle("ネットワークへの接続")
                    .setMessage("WIFIネットワークに接続できません")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }



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
                    speechText("わんぽーいんつ");
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


    // 以下、TTSの設定
    // VoiceActivityのものと同一なので読む必要はない
    @Override
    public void onInit(int status) {
        // TTS初期化
        if (TextToSpeech.SUCCESS == status) {
            Log.d(TAG, "initialized");
        } else {
            Log.e(TAG, "failed to initialize");
        }
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool buildSoundPool() {
        SoundPool pool;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            pool = new SoundPool.Builder().setAudioAttributes(attr).setMaxStreams(1).build();
        }

        return  pool;
    }

    private void speechText(String string) {
        String utteranceId = this.hashCode() + "";

        if (0 < string.length()) {
            if (tts.isSpeaking()) {
                tts.stop();
                return;
            }

            if (null != tts) {
                tts.setSpeechRate(1.0f);
                tts.setPitch(1.0f);
            }

            // Androidバージョンによって記法を若干かえる
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ttsGreater21(string, utteranceId);
            } else {
                ttsUnder20(string, utteranceId);
            }
        }
    }

    // 古いAndroidにおけるTTS呼び出し
    @SuppressWarnings("deprecation")
    private void ttsUnder20(String string, String utteranceId) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);
        tts.speak(string, TextToSpeech.QUEUE_FLUSH, map);
    }

    // 現在のAndroidにおけるTTS呼び出し
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String string, String utteranceId) {
        tts.speak(string, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != tts) {
            tts.shutdown();
        }
    }

}


