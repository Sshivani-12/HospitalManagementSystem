package com.project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hms.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {}

