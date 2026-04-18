package com.example.courseproject.model;

import java.util.List;

public class MissionResult {
    private final boolean threatNeutralized;
    private final List<Integer> survivingCrewIds;
    private final List<Integer> defeatedCrewIds;
    private final String log;

    public MissionResult(
            boolean threatNeutralized,
            List<Integer> survivingCrewIds,
            List<Integer> defeatedCrewIds,
            String log
    ) {
        this.threatNeutralized = threatNeutralized;
        this.survivingCrewIds = survivingCrewIds;
        this.defeatedCrewIds = defeatedCrewIds;
        this.log = log;
    }

    public boolean isThreatNeutralized() {
        return threatNeutralized;
    }

    public List<Integer> getSurvivingCrewIds() {
        return survivingCrewIds;
    }

    public List<Integer> getDefeatedCrewIds() {
        return defeatedCrewIds;
    }

    public String getLog() {
        return log;
    }
}

