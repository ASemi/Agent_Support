package com.example.shioya.agent_support;

/**
 * Created by shioya on 2017/03/14.
 */

// ポイント集計アクティビティで使うためのクラス
public class PlayerPoint {
    String name;
    int point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
