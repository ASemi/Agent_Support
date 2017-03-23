package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends Activity implements View.OnClickListener {

    BitmapFactory.Options opt = new BitmapFactory.Options();
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    Bitmap mBitmap1;
    Bitmap mBitmap2;
    Bitmap mBitmap3;
    Bitmap mBitmap4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        findViewById(R.id.backButton).setOnClickListener(this);
        imageButton1 = (ImageButton)findViewById(R.id.menuButton1);
        imageButton2 = (ImageButton)findViewById(R.id.menuButton2);
        imageButton3 = (ImageButton)findViewById(R.id.menuButton3);
        imageButton4 = (ImageButton)findViewById(R.id.menuButton4);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        opt.inJustDecodeBounds = false;
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.menucardlist, opt);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.menupepper, opt);
        mBitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.menupoint, opt);
        mBitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.menunineagent, opt);

        imageButton1.setImageBitmap(mBitmap1);
        imageButton2.setImageBitmap(mBitmap2);
        imageButton3.setImageBitmap(mBitmap3);
        imageButton4.setImageBitmap(mBitmap4);

    }

    @Override
    public void onStop() {
        super.onStop();
        mBitmap1.recycle();
        mBitmap2.recycle();
        mBitmap3.recycle();
        mBitmap4.recycle();
        imageButton1.setImageBitmap(null);
        imageButton2.setImageBitmap(null);
        imageButton3.setImageBitmap(null);
        imageButton4.setImageBitmap(null);
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
                Intent intentPoints = new Intent(this, PointActivity.class);
                startActivity(intentPoints);
                break;
            case R.id.menuButton4:
                Intent intentAgent9 = new Intent(this, Agent9Activity.class);
                startActivity(intentAgent9);
                break;
        }
    }
}
