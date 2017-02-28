package com.example.shioya.agent_support;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shioya on 2017/02/28.
 */

public class Strategies {
    ArrayList<String> name = new ArrayList<>();

    Strategies() {
        Collections.addAll(name, "prove","intercept","trap","transfer","lockon","decode","distribute","delete","counteract");
    }
}
