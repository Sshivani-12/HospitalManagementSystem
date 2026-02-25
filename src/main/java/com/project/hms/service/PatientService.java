package com.project.hms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.project.hms.entity.Patient;
import com.project.hms.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> getAll() {
        return repo.findAll();
    }

    public Patient save(Patient patient) {
        return repo.save(patient);
    }

    public Patient getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}