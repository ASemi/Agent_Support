package com.example.shioya.agent_support;

import java.util.ArrayList;

/**
 * Created by shioya on 2017/03/26.
 */

public class SubAgents {

    SubAgentName subAgentName;
    AgentAttribute agentAttribute;

    SubAgents(SubAgentName subAgentName) {
        this.subAgentName = subAgentName;
        this.agentAttribute = subAgentName.agentAttribute;
    }

    public static ArrayList<String> CreateFirst() {
        ArrayList<String> subAgentList = new ArrayList<>();

        for (SubAgentName subAgentName : SubAgentName.values()) {
            subAgentList.add(subAgentName.getName());
        }

        return subAgentList;
    }

}
