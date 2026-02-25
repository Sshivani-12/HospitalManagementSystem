package com.project.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import com.project.hms.entity.Appointment;
import com.project.hms.service.AppointmentService;
import com.project.hms.repository.PatientRepository;
import com.project.hms.repository.BillRepository;
import com.project.hms.repository.DoctorRepository;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	private final AppointmentService appointmentService;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final BillRepository billRepo;

	public AppointmentController(AppointmentService appointmentService, PatientRepository patientRepository,
			DoctorRepository doctorRepository, BillRepository billRepo) {
		this.appointmentService = appointmentService;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.billRepo = billRepo;
	}

	@GetMapping("/new")
	public String showForm(Model model) {

		model.addAttribute("appointment", new Appointment());
		model.addAttribute("patients", patientRepository.findAll());
		model.addAttribute("doctors", doctorRepository.findByAvailableTrue());

		return "appointment-form";
	}

	@GetMapping("/edit/{id}")
	public String editAppointment(@PathVariable Long id, Model model) {

		Appointment appointment = appointmentService.getById(id);

		model.addAttribute("appointment", appointment);
		model.addAttribute("patients", patientRepository.findAll());
		model.addAttribute("doctors", doctorRepository.findAll());

		return "appointment-form";
	}

	@GetMapping("/delete/{id}")
	public String deleteAppointment(@PathVariable Long id) {

		appointmentService.delete(id);

		return "redirect:/appointments";
	}

	@PostMapping("/save")
	public String saveAppointment(@Valid @ModelAttribute("appointment") Appointment appointment, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("patients", patientRepository.findAll());
			model.addAttribute("doctors", doctorRepository.findAll());
			return "appointment-form";
		}

		try {
			if (appointment.getStatus() == null) {
				appointment.setStatus("Scheduled");
			}

			appointmentService.createAppointment(appointment);

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());
			model.addAttribute("patients", patientRepository.findAll());
			model.addAttribute("doctors", doctorRepository.findAll());

			return "appointment-form";
		}

		return "redirect:/appointments";
	}

	@GetMapping
	public String listAppointments(Model model) {
		model.addAttribute("appointments", appointmentService.getAll());
		model.addAttribute("billRepository", billRepo); 
		return "appointment-list";
	}

	@GetMapping("/complete/{id}")
	public String markCompleted(@PathVariable Long id) {

		Appointment appointment = appointmentService.getById(id);

		if (appointment != null) {
			appointment.setStatus("Completed");
			appointmentService.update(appointment);
		}

		return "redirect:/appointments";
	}

	@GetMapping("/cancel/{id}")
	public String cancelAppointment(@PathVariable Long id) {

		Appointment appointment = appointmentService.getById(id);

		if (appointment != null) {
			appointment.setStatus("Cancelled");
			appointmentService.update(appointment);
		}

		return "redirect:/appointments";
	}
}