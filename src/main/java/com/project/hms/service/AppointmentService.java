package com.project.hms.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.hms.entity.Appointment;
import com.project.hms.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> getAll() {
        return repo.findAll();
    }

    public Appointment getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
    // CREATE NEW APPOINTMENT
    public void createAppointment(Appointment appointment) {

        LocalDateTime newTime = appointment.getAppointmentDateTime();
        Long doctorId = appointment.getDoctor().getId();

        // 🔹 Check doctor availability boolean
        if (!appointment.getDoctor().getAvailable()) {
            throw new RuntimeException("Doctor is marked as unavailable.");
        }

        // 🔹 30-minute slot check
        List<Appointment> doctorAppointments =
                repo.findByDoctorId(doctorId);

        for (Appointment existing : doctorAppointments) {

            LocalDateTime existingTime =
                    existing.getAppointmentDateTime();

            long minutesDifference =
                    Math.abs(Duration.between(existingTime, newTime).toMinutes());

            if (minutesDifference < 30) {
                throw new RuntimeException(
                        "Doctor is not available in this 30-minute slot."
                );
            }
        }

        // 🔹 Patient same time check
        boolean patientBusy =
                repo.existsByPatientIdAndAppointmentDateTime(
                        appointment.getPatient().getId(),
                        newTime
                );

        if (patientBusy) {
            throw new RuntimeException(
                    "Patient already has appointment at this time."
            );
        }

        repo.save(appointment);
    }

    public void update(Appointment appointment) {
        repo.save(appointment);
    }
}