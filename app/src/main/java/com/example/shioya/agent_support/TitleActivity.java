package com.example.shioya.agent_support;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TitleActivity extends Activity implements View.OnClickListener {
    private Handler mHandler = new Handler();
    private ScheduledExecutorService mScheduledExecutor;
    private TextView PressStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        findViewById(R.id.titleBack).setOnClickListener(this);
        PressStart = (TextView)findViewById(R.id.titleText);
        startMeasure();
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id) {
            case R.id.titleBack:
                Intent intentMenu = new Intent(this, MenuActivity.class);
                startActivity(intentMenu);
                break;
        }
    }

    // "PRESS START"の点滅
    // 参考（写経）
    // http://qiita.com/AbeHaruhiko/items/2b2618cebc996728cb50
    private void startMeasure() {
        mScheduledExecutor = Executors.newScheduledThreadPool(2);

        // 第一引数：繰り返し実行したい処理
        // 第二引数：指定時間後に第一引数の処理を開始
        // 第三引数：第一引数の処理完了後、指定時間後に再実行
        // 第四引数：第二、第三引数の単位
        //
        // new Runnableを0秒後に実行し、完了後1700ミリ秒ごとに繰り返す

        mScheduledExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        PressStart.setVisibility(View.VISIBLE);

                        // HONEYCONBより古いバージョンはProperty Animaton非対応
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            animateAlpha();
                        }
                    }
                });
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            private void animateAlpha() {
                // 実行するAnimatorのリスト
                List<Animator> animatorList = new ArrayList<Animator>();

                // alpha値を0から1に
                ObjectAnimator animeFadeIn = ObjectAnimator.ofFloat(PressStart, "alpha", 0f, 1f);
                animeFadeIn.setDuration(1000);

                // alpha値を1から0に
                ObjectAnimator animeFadeOut = ObjectAnimator.ofFloat(PressStart, "alpha", 1f, 0f);
                animeFadeOut.setDuration(600);

                // 実行対象Animatorリストへの追加
                animatorList.add(animeFadeIn);
                animatorList.add(animeFadeOut);

                final AnimatorSet animatorSet = new AnimatorSet();

                // リスト順に実行
                animatorSet.playSequentially(animatorList);
                animatorSet.start();
            }
        }, 0, 1700, TimeUnit.MILLISECONDS);
    }
}
