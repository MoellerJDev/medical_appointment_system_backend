package com.medical.medical_appointment_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medical.medical_appointment_system.model.Doctor;
import com.medical.medical_appointment_system.model.Specialization;
import com.medical.medical_appointment_system.repository.DoctorRepository;
import com.medical.medical_appointment_system.repository.SpecializationRepository;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    // Create a new doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        if (doctor.getSpecialization() != null && doctor.getSpecialization().getId() != null) {
            Specialization specialization = specializationRepository.findById(doctor.getSpecialization().getId())
                    .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + doctor.getSpecialization().getId()));
            doctor.setSpecialization(specialization);
        }
        Doctor savedDoctor = doctorRepository.save(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }

    // Get doctors by specialization ID
    @GetMapping("/specialization/{specializationId}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable Long specializationId) {
        List<Doctor> doctors = doctorRepository.findBySpecializationId(specializationId);
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctors);
    }

    // Get a doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        return ResponseEntity.ok(doctor);
    }

    // Update an existing doctor
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

        if (updatedDoctor.getSpecialization() != null && updatedDoctor.getSpecialization().getId() != null) {
            Specialization specialization = specializationRepository.findById(updatedDoctor.getSpecialization().getId())
                    .orElseThrow(() -> new RuntimeException("Specialization not found with ID: " + updatedDoctor.getSpecialization().getId()));
            doctor.setSpecialization(specialization);
        }

        doctor.setName(updatedDoctor.getName());
        Doctor savedDoctor = doctorRepository.save(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    // Delete a doctor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}