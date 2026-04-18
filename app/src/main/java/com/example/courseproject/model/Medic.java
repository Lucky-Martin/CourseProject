package com.example.courseproject.model;

public class Medic extends CrewMember {
    public Medic(int id, String name, Location initialLocation) {
        super(id, name, Specialization.MEDIC, 7, 2, 18, initialLocation);
    }

    @Override
    protected int specializationDamageBonus(Threat threat) {
        return -1;
    }
}

