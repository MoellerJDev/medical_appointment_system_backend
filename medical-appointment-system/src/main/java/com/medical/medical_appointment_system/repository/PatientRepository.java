package com.medical.medical_appointment_system.repository;

import java.time.LocalDate; // Use Java's native LocalDate
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.medical_appointment_system.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}