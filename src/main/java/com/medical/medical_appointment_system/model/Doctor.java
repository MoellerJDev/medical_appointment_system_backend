package com.medical.medical_appointment_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false) // Prevents null values in the database
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false) // Links to specialization and ensures it's not null
    private Specialization specialization;

    // Default constructor
    public Doctor() {}

    // Parameterized constructor
    public Doctor(String name, Specialization specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    // Override toString
    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization=" + (specialization != null ? specialization.getName() : "N/A") +
                '}';
    }
}