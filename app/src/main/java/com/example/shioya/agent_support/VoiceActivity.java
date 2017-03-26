package com.example.shioya.agent_support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class VoiceActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private static final String TAG = "TestTTS";
    double rand_select_1p = 1.0;
    double rand_select_nice = 1.0;

    SoundPool soundPool;
    Button buttonOne;
    int soundOne;
    String defaultString_1p ="１ポイント";
    SpannableString spanString_1p;

    Button buttonNice;
    int soundNice;
    String defaultString_nice = "ナイスゥ";
    SpannableString spanString_nice;

    private SeekBar pitchseekBar;
    private SeekBar speedseekBar;
    private TextView pitchtext;
    private TextView speedtext;
    private float pitchnum = 1.0f;
    private float speednum = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplevoice);
        findViewById(R.id.backButton).setOnClickListener(this);
        findViewById(R.id.buttonKiyoi).setOnClickListener(this);
        findViewById(R.id.buttonJuntaku).setOnClickListener(this);
        buttonOne = (Button)findViewById(R.id.buttonOnepoint);
        buttonOne.setOnClickListener(this);
        findViewById(R.id.buttonSibui).setOnClickListener(this);
        buttonNice = (Button) findViewById(R.id.buttonNice);
        buttonNice.setOnClickListener(this);
        findViewById(R.id.buttonReach).setOnClickListener(this);
        findViewById(R.id.buttonImnot).setOnClickListener(this);
        findViewById(R.id.buttonKyohi).setOnClickListener(this);
        findViewById(R.id.buttonGokuhi).setOnClickListener(this);
        findViewById(R.id.buttonSinten).setOnClickListener(this);
        findViewById(R.id.buttonKoukai).setOnClickListener(this);
        findViewById(R.id.buttonHosoku).setOnClickListener(this);
        findViewById(R.id.buttonSyomei).setOnClickListener(this);
        findViewById(R.id.buttonGodou).setOnClickListener(this);

        // シークバー周りの設定
        pitchseekBar = (SeekBar)findViewById(R.id.pitchSeekBar);
        speedseekBar = (SeekBar)findViewById(R.id.speedSeekBar);
        pitchtext = (TextView)findViewById(R.id.pitchText);
        speedtext = (TextView)findViewById(R.id.speedText);

        pitchtext.setText(String.format("%.1f", getConvertedValue(pitchseekBar.getProgress())));
        speedtext.setText(String.format("%.1f", getConvertedValue(speedseekBar.getProgress())));

        pitchseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // ツマミをドラッグしたときによばれる
                pitchtext.setText(String.format("%.1f", getConvertedValue(pitchseekBar.getProgress())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // ツマミを離したときに呼ばれる
                pitchnum = getConvertedValue(pitchseekBar.getProgress());
            }
        });

        speedseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speedtext.setText(String.format("%.1f", getConvertedValue(speedseekBar.getProgress())));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                speednum = getConvertedValue(speedseekBar.getProgress());
            }
        });


        // TTSインスタンスの生成
        tts = new TextToSpeech(this, this);

        // soundpoolインスタンス
        soundPool = buildSoundPool();
        soundOne = soundPool.load(this, R.raw.onepoints2, 1);
        spanString_1p = new SpannableString(defaultString_1p);
        spanString_1p.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString_1p.length(), 0);

        soundNice = soundPool.load(this, R.raw.niceu, 1);
        spanString_nice = new SpannableString(defaultString_nice);
        spanString_nice.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString_nice.length(), 0);

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
                if (rand_select_1p <= 0.1) {
                    soundPool.play(soundOne, 1.0f, 1.0f, 0, 0, 1);
                } else {
                    speechText("わんぽーいんつ");
                }
                rand_select_1p = Math.random();
                if (rand_select_1p <= 0.1) {
                    buttonOne.setText(spanString_1p);
                } else {
                    buttonOne.setText(defaultString_1p);
                }
                break;
            case R.id.buttonSibui:
                speechText("渋い");
                break;
            case R.id.buttonNice:
                if (rand_select_nice <= 0.1) {
                    soundPool.play(soundNice, 1.0f, 1.0f, 0, 0, 1);
                } else {
                    speechText("ナイスー");
                }
                rand_select_nice = Math.random();
                if (rand_select_nice <= 0.1) {
                    buttonNice.setText(spanString_nice);
                } else {
                    buttonNice.setText(defaultString_nice);
                }
                break;
            case R.id.buttonReach:
                speechText("リーチ");
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
                speechText("ごどう");
                break;
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
                tts.setSpeechRate(speednum);
                tts.setPitch(pitchnum);
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

    // SeekBarの値をpitch, speec用のfloat値に変換
    public float getConvertedValue(int intVal) {
        int tmp = intVal + 1;    // 1<= tmp <= 20
        float floatVal = 0.1f * tmp;

        return floatVal;
    }


}
