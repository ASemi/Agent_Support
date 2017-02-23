package com.example.shioya.agent_support;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imageButton1 = (ImageButton)findViewById(R.id.detectiveButton);
        imageButton1.setImageResource(R.drawable.nightmare);

        ImageView imageView1 = (ImageView)findViewById(R.id.detectiveText);
        imageView1.setImageResource(R.drawable.nightmare_text);
    }
}
