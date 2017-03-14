package com.example.shioya.agent_support;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shioya on 2017/02/28.
 */

public class Agents {
    ArrayList<String> name = new ArrayList<>();

    Agents() {
        Collections.addAll(name, "speaker","tank","obliqueshadow","titanium","larklady","swanmaiden","huntress","blackhand","nightmare","baronbrumaire","markjunior","staticelectricity",
                "viper","angel","savageassassin","doubleknight","kwonkiku","darkflow","diamondman","perfume","toughgun","redblade","alias","backfire","trinity","j","detective",
        "chainer", "psychopass", "goldhead", "neutral", "gamemaster", "duelist", "handstander", "smallspace", "filler", "momentsleep", "vitalityrecorder", "chairman", "amnesia",
        "tipsy", "pepper", "juggler", "kurosawa", "lifegamer", "sunflower", "fishcake", "host", "morpheus","trumpeter", "keyboarder", "julius", "fontjunkie", "sprincar", "blackpepper", "shellkeaper", "iphone");
    }


    AgentName agentName;
    AgentAttribute agentAttribute;
    Gender gender;
    AgentPackage agentPackage;

    Agents(AgentName agentName) {
        this.agentName = agentName;
        this.agentAttribute = agentName.agentAttribute;
        this.gender = agentName.gender;
        this.agentPackage = agentName.agentPackage;
    }

    public static ArrayList<String> CreateFirst() {
        ArrayList<String> agentlist = new ArrayList<>();

        for (AgentName agentName : AgentName.values()) {
            agentlist.add(agentName.getName());
        }

        return agentlist;
    }

    public static ArrayList<String> SetList(AgentPackage ap, Gender gen, AgentAttribute aa) {
        ArrayList<String> agentlist = new ArrayList<>();

        for (AgentName agentName : AgentName.values()) {
            if (ap == agentName.agentPackage || ap == AgentPackage.ALL) {
                if (gen == agentName.gender || gen == Gender.ALL) {
                    if (aa == agentName.agentAttribute || aa == AgentAttribute.ALL) {
                        agentlist.add(agentName.getName());
                    }
                }
            }
        }

        return agentlist;
    }
}
