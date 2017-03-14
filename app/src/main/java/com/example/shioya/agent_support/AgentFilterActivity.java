// エージェント検索をするときのフィルターのアクティビティ
// 対応するレイアウト：select_filter.xml

package com.example.shioya.agent_support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.view.View;
import android.widget.Button;

public class AgentFilterActivity extends Activity implements View.OnClickListener {

    Button[]package_btns = new Button[3];
    Button[]gender_btns = new Button[4];
    Button[]attr_btns = new Button[4];
    Agents selected = new Agents(AgentName.ALLFILTER);
    private static final int PACKAGE_CODE = 0;
    private static final int GENDER_CODE = 1;
    private static final int ATTR_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_filter);

        package_btns[0] = (Button)findViewById(R.id.buttonPackage0);  // 既存
        package_btns[1] = (Button)findViewById(R.id.buttonPackage1);  // 拡張
        package_btns[2] = (Button)findViewById(R.id.buttonPackage2);  // 選択解除

        gender_btns[0] = (Button)findViewById(R.id.buttonGender0);    // 男
        gender_btns[1] = (Button)findViewById(R.id.buttonGender1);    // 女
        gender_btns[2] = (Button)findViewById(R.id.buttonGender2);    // なし
        gender_btns[3] = (Button)findViewById(R.id.buttonGender3);    // 選択解除

        attr_btns[0] = (Button)findViewById(R.id.buttonAttr0);        // シークレット
        attr_btns[1] = (Button)findViewById(R.id.buttonAttr1);        // パブリック
        attr_btns[2] = (Button)findViewById(R.id.buttonAttr2);        // ノーマル
        attr_btns[3] = (Button)findViewById(R.id.buttonAttr3);        // 選択解除

        for (Button b : package_btns) {
            b.setOnClickListener(this);
            b.setEnabled(true);
        }
        package_btns[2].setEnabled(false);

        for (Button b : gender_btns) {
            b.setOnClickListener(this);
            b.setEnabled(true);
        }
        gender_btns[3].setEnabled(false);

        for (Button b : attr_btns) {
            b.setOnClickListener(this);
            b.setEnabled(true);
        }
        attr_btns[3].setEnabled(false);

        findViewById(R.id.buttonOK).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.buttonCancel:
                Intent intent0 = new Intent();
                setResult(RESULT_CANCELED, intent0);
                finish();
                break;
            case R.id.buttonPackage0:
            case R.id.buttonPackage1:
            case R.id.buttonPackage2:
                changeButtonState(package_btns, "buttonPackage", id, PACKAGE_CODE);
                break;
            case R.id.buttonGender0:
            case R.id.buttonGender1:
            case R.id.buttonGender2:
            case R.id.buttonGender3:
                changeButtonState(gender_btns, "buttonGender", id, GENDER_CODE);
                break;
            case R.id.buttonAttr0:
            case R.id.buttonAttr1:
            case R.id.buttonAttr2:
            case R.id.buttonAttr3:
                changeButtonState(attr_btns, "buttonAttr", id, ATTR_CODE);
                break;
            case R.id.buttonOK:
                Intent intent1 = new Intent();
                intent1.putExtra("agentpackage", selected.agentPackage);
                intent1.putExtra("gender", selected.gender);
                intent1.putExtra("agentattribute", selected.agentAttribute);
                setResult(RESULT_OK, intent1);
                finish();
                break;
        }
    }


    private void changeButtonState(Button[] buttons, String buttonid, int id, int code) {
        int j;
        for (Button b : buttons) {
            b.setEnabled(true);
        }
        for (j=0; j < buttons.length; j++) {
            String strNo = Integer.toString(j);
            if (id == getResources().getIdentifier(buttonid+strNo, "id", getPackageName())) {
                changeSelectedState(code, j);
                buttons[j].setEnabled(false);
                break;
            }
        }
        return;
    }

    private void changeSelectedState(int enumcode, int id) {
        switch (enumcode) {
            case PACKAGE_CODE:
                selected.agentPackage = AgentPackage.values()[id];
                break;
            case GENDER_CODE:
                selected.gender = Gender.values()[id];
                break;
            case ATTR_CODE:
                selected.agentAttribute = AgentAttribute.values()[id];
                break;
        }

        return;
    }


}
