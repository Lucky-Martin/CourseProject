package com.example.courseproject.model;

public class Scientist extends CrewMember {
    public Scientist(int id, String name, Location initialLocation) {
        super(id, name, Specialization.SCIENTIST, 8, 1, 17, initialLocation);
    }

    @Override
    protected int specializationDamageBonus(Threat threat) {
        return 1;
    }
}

