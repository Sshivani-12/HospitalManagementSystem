package com.project.hms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.hms.entity.Appointment;
import com.project.hms.entity.Doctor;
import com.project.hms.entity.Patient;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByDoctorId(Long doctorId);

	List<Appointment> findByPatientId(Long patientId);

	List<Appointment> findByDoctorAndAppointmentDateTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);

	List<Appointment> findByPatientAndAppointmentDateTimeBetween(Patient patient, LocalDateTime start,
			LocalDateTime end);

	boolean existsByDoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime appointmentDateTime);

	// Check patient slot
	boolean existsByPatientIdAndAppointmentDateTime(Long patientId, LocalDateTime appointmentDateTime);
}