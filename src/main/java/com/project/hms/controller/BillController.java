package com.project.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.hms.entity.*;
import com.project.hms.service.*;

@Controller
@RequestMapping("/bills")
public class BillController {

	private final BillService billService;
	private final AppointmentService appointmentService;

	public BillController(BillService billService, AppointmentService appointmentService) {
		this.billService = billService;
		this.appointmentService = appointmentService;
	}

	// View all bills
	@GetMapping
	public String listBills(Model model) {
		model.addAttribute("bills", billService.getAll());
		return "bill-list";
	}

	// Mark as paid
	@GetMapping("/pay/{id}")
	public String markPaid(@PathVariable Long id) {

		Bill bill = billService.getById(id);

		if (bill != null) {
			bill.setPaymentStatus("Paid");
			billService.save(bill);
		}

		return "redirect:/bills";
	}

	// Delete bill
	@GetMapping("/delete/{id}")
	public String deleteBill(@PathVariable Long id) {
		billService.delete(id);
		return "redirect:/bills";
	}

	@GetMapping("/generate/{id}")
	public String generateBill(@PathVariable Long id) {

		Appointment appointment = appointmentService.getById(id);

		if (appointment != null && "Completed".equals(appointment.getStatus())) {
			billService.generateBill(appointment);
		}
		return "redirect:/bills";
	}
}