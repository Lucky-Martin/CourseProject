package com.example.courseproject.model;

public class Threat {
    private final String name;
    private final int skill;
    private final int resilience;

    private int energy;
    private final int maxEnergy;

    public Threat(String name, int skill, int resilience, int maxEnergy) {
        this.name = name;
        this.skill = skill;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
    }

    public String getName() {
        return name;
    }

    public int getSkill() {
        return skill;
    }

    public int getResilience() {
        return resilience;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public boolean isDefeated() {
        return energy <= 0;
    }

    public void takeDamage(int damage) {
        if (damage <= 0) return;
        energy -= damage;
        if (energy < 0) energy = 0;
    }

    public int calculateDamageDealt(CrewMember target) {
        int base = skill - target.getResilience();
        return Math.max(0, base);
    }
}

