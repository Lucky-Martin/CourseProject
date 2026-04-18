package com.example.courseproject.model;

public class Soldier extends CrewMember {
    public Soldier(int id, String name, Location initialLocation) {
        super(id, name, Specialization.SOLDIER, 9, 0, 16, initialLocation);
    }

    @Override
    protected int specializationDamageBonus(Threat threat) {
        return 1;
    }
}

