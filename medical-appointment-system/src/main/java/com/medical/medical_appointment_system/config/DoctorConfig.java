package com.medical.medical_appointment_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medical.medical_appointment_system.model.Doctor;
import com.medical.medical_appointment_system.model.Specialization;
import com.medical.medical_appointment_system.repository.DoctorRepository;
import com.medical.medical_appointment_system.repository.SpecializationRepository;

@Configuration
public class DoctorConfig {

    @Bean
    CommandLineRunner seedDatabase(SpecializationRepository specializationRepository, DoctorRepository doctorRepository) {
        return args -> {
            // Seed specializations if not already present
            if (specializationRepository.count() == 0) {
                Specialization cardiology = specializationRepository.save(new Specialization(null, "Cardiology"));
                Specialization pediatrics = specializationRepository.save(new Specialization(null, "Pediatrics"));
                Specialization dermatology = specializationRepository.save(new Specialization(null, "Dermatology"));
                Specialization orthopedics = specializationRepository.save(new Specialization(null, "Orthopedics"));
                Specialization neurology = specializationRepository.save(new Specialization(null, "Neurology"));

                // Seed doctors for each specialization
                doctorRepository.save(new Doctor("Dr. Smith", cardiology));
                doctorRepository.save(new Doctor("Dr. Adams", cardiology));
                doctorRepository.save(new Doctor("Dr. Brown", cardiology));

                doctorRepository.save(new Doctor("Dr. Taylor", pediatrics));
                doctorRepository.save(new Doctor("Dr. Johnson", pediatrics));
                doctorRepository.save(new Doctor("Dr. Wilson", pediatrics));

                doctorRepository.save(new Doctor("Dr. Miller", dermatology));
                doctorRepository.save(new Doctor("Dr. Davis", dermatology));
                doctorRepository.save(new Doctor("Dr. Clark", dermatology));

                doctorRepository.save(new Doctor("Dr. Lopez", orthopedics));
                doctorRepository.save(new Doctor("Dr. Gonzalez", orthopedics));
                doctorRepository.save(new Doctor("Dr. Hernandez", orthopedics));

                doctorRepository.save(new Doctor("Dr. Hall", neurology));
                doctorRepository.save(new Doctor("Dr. Martinez", neurology));
                doctorRepository.save(new Doctor("Dr. Turner", neurology));
            }
        };
    }
}