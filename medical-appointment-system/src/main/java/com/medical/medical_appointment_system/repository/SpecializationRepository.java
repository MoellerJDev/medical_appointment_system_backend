package com.medical.medical_appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.medical_appointment_system.model.Specialization;

// Repository interface for Specialization entity
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    // Additional custom queries can be added here, if needed

    // Example of a custom query method to find a specialization by its name
    // Optional<Specialization> findByName(String name);
}