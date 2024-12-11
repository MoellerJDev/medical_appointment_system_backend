package com.medical.medical_appointment_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medical.medical_appointment_system.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Find doctors by specialization ID
    List<Doctor> findBySpecializationId(Long specializationId);

    // (Optional) If you need to keep the query by name, ensure it is used sparingly
    List<Doctor> findBySpecialization_Name(String name);
}