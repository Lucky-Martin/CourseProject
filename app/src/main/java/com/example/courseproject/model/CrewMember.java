package com.example.courseproject.model;

public abstract class CrewMember {
    private final int id;
    private final String name;
    private final Specialization specialization;

    private int skill;
    private final int resilience;

    private int experience;
    private int energy;
    private final int maxEnergy;

    private Location location;

    protected CrewMember(
            int id,
            String name,
            Specialization specialization,
            int baseSkill,
            int resilience,
            int maxEnergy,
            Location initialLocation
    ) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.skill = baseSkill;
        this.resilience = resilience;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.experience = 0;
        this.location = initialLocation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public int getSkill() {
        return skill;
    }

    public int getResilience() {
        return resilience;
    }

    public int getExperience() {
        return experience;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLocation) {
        this.location = newLocation;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void takeDamage(int damage) {
        if (damage <= 0) return;
        energy -= damage;
        if (energy < 0) {
            energy = 0;
        }
    }

    public void addExperience(int xp) {
        if (xp <= 0) return;
        experience += xp;
        skill += xp;
    }

    public void restoreEnergy() {
        energy = maxEnergy;
    }

    public int calculateDamageDealt(Threat threat) {
        int base = skill - threat.getResilience();
        int bonus = specializationDamageBonus(threat);
        return Math.max(0, base + bonus);
    }

    protected int specializationDamageBonus(Threat threat) {
        return 0;
    }
}

