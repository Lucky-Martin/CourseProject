package com.example.courseproject.model;

public class Engineer extends CrewMember {
    public Engineer(int id, String name, Location initialLocation) {
        super(id, name, Specialization.ENGINEER, 6, 3, 19, initialLocation);
    }
}

