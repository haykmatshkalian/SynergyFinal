package com.example.dailydriver.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "household")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            mappedBy = "household",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Citizen> members = new ArrayList<>();

    public void addMember(Citizen citizen) {
        members.add(citizen);
        citizen.setHousehold(this);
    }

    public void removeMember(Citizen citizen) {
        members.remove(citizen);
        citizen.setHousehold(null);
    }

    public Long getId() {
        return id;
    }

    public List<Citizen> getMembers() {
        return members;
    }
}
