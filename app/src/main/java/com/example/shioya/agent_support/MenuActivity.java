package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        findViewById(R.id.backButton).setOnClickListener(this);
        findViewById(R.id.menuButton1).setOnClickListener(this);
        findViewById(R.id.menuButton2).setOnClickListener(this);
        findViewById(R.id.menuButton3).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.backButton:
                finish();
                break;
            case R.id.menuButton1:
                Intent intentCard = new Intent(this, MainActivity.class);
                startActivity(intentCard);
                break;
            case R.id.menuButton2:
                Intent intentTools = new Intent(this, PepperActivity.class);
                startActivity(intentTools);
                break;
            case R.id.menuButton3:
                Intent intentVoices = new Intent(this, VoiceActivity.class);
                startActivity(intentVoices);
                break;
        }
    }
}
