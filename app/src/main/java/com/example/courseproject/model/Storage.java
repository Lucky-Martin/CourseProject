package com.example.courseproject.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private static Storage instance;

    private final Map<Integer, CrewMember> crewMembers = new HashMap<>();
    private int nextCrewId = 1;

    private int completedMissions = 0;
    private int totalCrewRecruited = 0;
    private int trainingSessions = 0;

    private Storage() {
    }

    public static synchronized Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public int getCompletedMissions() {
        return completedMissions;
    }

    public void incrementCompletedMissions() {
        completedMissions++;
    }

    public int getTotalCrewRecruited() {
        return totalCrewRecruited;
    }

    public int getTrainingSessions() {
        return trainingSessions;
    }

    public void incrementTrainingSessions() {
        trainingSessions++;
    }

    public int addCrewMember(String nickname, Specialization specialization, Location initialLocation) {
        int id = nextCrewId++;
        totalCrewRecruited++;
        CrewMember member;
        switch (specialization) {
            case PILOT:
                member = new Pilot(id, nickname, initialLocation);
                break;
            case ENGINEER:
                member = new Engineer(id, nickname, initialLocation);
                break;
            case MEDIC:
                member = new Medic(id, nickname, initialLocation);
                break;
            case SCIENTIST:
                member = new Scientist(id, nickname, initialLocation);
                break;
            case SOLDIER:
                member = new Soldier(id, nickname, initialLocation);
                break;
            default:
                throw new IllegalStateException("Unknown: " + specialization);
        }

        crewMembers.put(id, member);
        return id;
    }

    public CrewMember getCrewMember(int crewId) {
        return crewMembers.get(crewId);
    }

    public void moveCrewMember(int crewId, Location newLocation) {
        CrewMember member = crewMembers.get(crewId);
        if (member == null) return;
        member.setLocation(newLocation);
        if (newLocation == Location.QUARTERS) {
            member.restoreEnergy();
        }
    }

    public void removeCrewMember(int crewId) {
        crewMembers.remove(crewId);
    }

    public List<CrewMember> listCrewByLocation(Location location) {
        List<CrewMember> list = new ArrayList<>();
        for (CrewMember member : crewMembers.values()) {
            if (member.getLocation() == location) {
                list.add(member);
            }
        }
        return list;
    }

    public int countCrewByLocation(Location location) {
        int count = 0;
        for (CrewMember member : crewMembers.values()) {
            if (member.getLocation() == location) {
                count++;
            }
        }
        return count;
    }

    public int countCrewBySpecialization(Specialization specialization) {
        int count = 0;
        for (CrewMember member : crewMembers.values()) {
            if (member.getSpecialization() == specialization) {
                count++;
            }
        }
        return count;
    }
}

