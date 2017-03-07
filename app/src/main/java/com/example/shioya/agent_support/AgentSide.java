package com.example.shioya.agent_support;

import android.graphics.Color;

/**
 * Created by shioya on 2017/03/07.
 */

// プレイヤーの陣営をカラーリングするためのクラス
public class AgentSide {
    int side;
    int[]colors = {Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW};

    // コンストラクタ
    AgentSide() {
        side = colors[0];
    }
}
