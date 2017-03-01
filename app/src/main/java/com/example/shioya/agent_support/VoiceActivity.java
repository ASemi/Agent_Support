package com.example.shioya.agent_support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class VoiceActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private static final String TAG = "TestTTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplevoice);

        // TTSインスタンスの生成
        tts = new TextToSpeech(this, this);

    }

    @Override
    public void onInit(int status) {
        // TTS初期化
        if (TextToSpeech.SUCCESS == status) {
            Log.d(TAG, "initialized");
        } else {
            Log.e(TAG, "failed to initialize");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.backButton:
                finish();
                break;
            case R.id.buttonKiyoi:
                speechText("きよい");
                break;
            case R.id.buttonJuntaku:
                speechText("潤沢");
                break;
            case R.id.buttonOnepoint:
                speechText("わんぽーいんつ");
                break;
            case R.id.buttonSibui:
                speechText("渋い");
                break;
            case R.id.buttonImnot:
                speechText("私は違う");
                break;
            case R.id.buttonKyohi:
                speechText("拒否");
                break;
            case R.id.buttonGokuhi:
                speechText("極秘");
                break;
            case R.id.buttonSinten:
                speechText("親展");
                break;
            case R.id.buttonKoukai:
                speechText("公開");
                break;
            case R.id.buttonSyomei:
                speechText("証明");
                break;
            case R.id.buttonHosoku:
                speechText("捕捉");
                break;
            case R.id.buttonGodou:
                speechText("誤導");
                break;
        }

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

    protected void onDestroy() {
        super.onDestroy();
        if (null != tts) {
            tts.shutdown();
        }
    }
}
