package com.project.hms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.hms.entity.Doctor;
import com.project.hms.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository repo;

    public DoctorService(DoctorRepository repo) {
        this.repo = repo;
    }

    public List<Doctor> getAll() {
        return repo.findAll();
    }

    public Doctor save(Doctor doctor) {
        return repo.save(doctor);
    }

    public Doctor getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}