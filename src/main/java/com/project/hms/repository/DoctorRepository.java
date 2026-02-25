package com.project.hms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hms.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // CHECK WHICH ALL DOCTORS ARE AVAILALE
	List<Doctor> findByAvailableTrue();
}
