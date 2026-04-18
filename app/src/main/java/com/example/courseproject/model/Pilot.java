package com.example.courseproject.model;

public class Pilot extends CrewMember {
    public Pilot(int id, String name, Location initialLocation) {
        super(id, name, Specialization.PILOT, 5, 4, 20, initialLocation);
    }

    @Override
    protected int specializationDamageBonus(Threat threat) {
        return 1;
    }
}

