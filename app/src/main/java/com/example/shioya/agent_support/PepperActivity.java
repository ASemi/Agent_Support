package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;

public class PepperActivity extends Activity implements View.OnClickListener {

    ImageView[]pepper_list = new ImageView[6];
    public int iden_counter = 0;
    Resources res;
    Drawable drawable;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pepper_iden);

        for (i=1; i<=6; i++) {
            String strNo = Integer.toString(i);
            pepper_list[i-1]=(ImageView)findViewById(getResources().getIdentifier("pepper_image"+strNo , "id" , getPackageName()));
        }
        res = getResources();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            drawable = ResourcesCompat.getDrawable(res, R.drawable.pepper_s, null);
        } else {
            drawable = res.getDrawable(R.drawable.pepper_s);
        }

        findViewById(R.id.backButton).setOnClickListener(this);

        findViewById(R.id.iden_success).setOnClickListener(this);
        findViewById(R.id.minus1).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.backButton:
                finish();
                break;
            case R.id.iden_success:
                pepper_list[iden_counter++].setImageDrawable(drawable);
                break;
            case R.id.minus1:
                pepper_list[--iden_counter].setImageDrawable(null);
                break;
        }
    }
}
