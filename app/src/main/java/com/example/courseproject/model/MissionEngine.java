package com.example.courseproject.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissionEngine {

    private final Storage storage;

    public MissionEngine(Storage storage) {
        this.storage = storage;
    }

    public MissionResult launchMission(List<Integer> crewIds) {
        List<CrewMember> activeCrew = new ArrayList<>();
        List<Integer> defeated = new ArrayList<>();

        for (int crewId : crewIds) {
            CrewMember crew = storage.getCrewMember(crewId);
            if (crew == null) {
                continue;
            }
            if (crew.isAlive()) {
                activeCrew.add(crew);
            } else {
                defeated.add(crewId);
            }
        }

        if (activeCrew.isEmpty()) {
            return new MissionResult(false, new ArrayList<>(), defeated, "No living crew members selected.");
        }

        int missionNumber = storage.getCompletedMissions() + 1;
        Threat threat = generateThreat(missionNumber);

        List<Integer> surviving = new ArrayList<>();
        StringBuilder log = new StringBuilder();

        log.append("=== MISSION ").append(missionNumber).append(" ===\n");
        log.append("Threat: ").append(threat.getName())
                .append(" (skill: ").append(threat.getSkill())
                .append(", res: ").append(threat.getResilience())
                .append(", energy: ").append(threat.getEnergy()).append("/").append(threat.getMaxEnergy()).append(")\n");

        for (int i = 0; i < activeCrew.size(); i++) {
            CrewMember crew = activeCrew.get(i);
            log.append("Crew ").append(i + 1).append(": ").append(crew.getName()).append(" (")
                    .append(crew.getSpecialization()).append(") skill: ")
                    .append(crew.getSkill()).append("; res: ").append(crew.getResilience())
                    .append("; exp: ").append(crew.getExperience())
                    .append("; energy: ").append(crew.getEnergy()).append("/").append(crew.getMaxEnergy()).append("\n");
        }

        log.append("--- Round 1 ---\n");

        int round = 1;
        int turn = 0;
        while (threat.getEnergy() > 0 && !activeCrew.isEmpty()) {
            if (turn > 0 && turn % activeCrew.size() == 0) {
                round++;
                log.append("--- Round ").append(round).append(" ---\n");
            }

            CrewMember attacker = activeCrew.get(turn % activeCrew.size());
            log.append(attacker.getName()).append(" acts against ").append(threat.getName()).append("\n");

            int damageDealt = attacker.calculateDamageDealt(threat);
            threat.takeDamage(damageDealt);
            log.append("Damage dealt: ").append(damageDealt)
                    .append(" -> Threat energy: ").append(threat.getEnergy()).append("/").append(threat.getMaxEnergy()).append("\n");

            if (threat.getEnergy() <= 0) {
                break;
            }

            log.append(threat.getName()).append(" retaliates against ").append(attacker.getName()).append("\n");
            int damageTaken = threat.calculateDamageDealt(attacker);
            attacker.takeDamage(damageTaken);

            log.append("Damage dealt: ").append(damageTaken)
                    .append(" -> ").append(attacker.getName())
                    .append(" energy: ").append(attacker.getEnergy()).append("/").append(attacker.getMaxEnergy()).append("\n");

            if (!attacker.isAlive()) {
                log.append("=== ").append(attacker.getName()).append(" is defeated! ===\n");
                storage.removeCrewMember(attacker.getId());
                defeated.add(attacker.getId());
                activeCrew.remove(attacker);
                if (activeCrew.isEmpty()) {
                    break;
                }
                turn--;
            }

            turn++;
        }

        boolean threatNeutralized = threat.getEnergy() <= 0;
        if (threatNeutralized) {
            storage.incrementCompletedMissions();
            int xpGained = 1;
            log.append("=== MISSION COMPLETE ===\n");

            for (CrewMember crew : activeCrew) {
                if (storage.getCrewMember(crew.getId()) != null) {
                    crew.addExperience(xpGained);
                    surviving.add(crew.getId());
                    log.append(crew.getName()).append(" gains ").append(xpGained).append(" experience point. (exp: ")
                            .append(crew.getExperience()).append(")\n");
                }
            }
        } else {
            log.append("=== MISSION FAILED ===\n");
            for (int crewId : defeated) {
                log.append("Crew member ").append(crewId).append(" is lost.\n");
            }
        }

        return new MissionResult(threatNeutralized, surviving, defeated, log.toString().trim());
    }

    private Threat generateThreat(int missionNumber) {
        List<String> threatNames = Arrays.asList(
                "Zombie Horde",
                "The Swarm",
                "Ravenous Walkers",
                "Undead Breach",
                "Graveyard Outbreak",
                "Night of the Living Dead",
                "Infected Siege"
        );
        String name = threatNames.get((missionNumber - 1) % threatNames.size());

        int skill = 4 + missionNumber;
        int resilience = 1 + (missionNumber / 3);
        int maxEnergy = 20 + (missionNumber * 4);

        return new Threat(name, skill, resilience, maxEnergy);
    }
}

