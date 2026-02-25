package com.project.hms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.hms.entity.Appointment;
import com.project.hms.entity.Bill;
import com.project.hms.repository.BillRepository;

@Service
public class BillService {

    private final BillRepository repository;

    public BillService(BillRepository repository) {
        this.repository = repository;
    }

    public List<Bill> getAll() {
        return repository.findAll();
    }

    public Bill getById(Long id) {
        return repository.findById(id).orElse(null);
    }

	public Bill save(Bill bill) {
		return repository.save(bill);
	  }

    public Bill generateBill(Appointment appointment) {

        // Check if bill already exists
        if (repository.existsByAppointmentId(appointment.getId())) {
            return repository.findByAppointmentId(appointment.getId());
        }

        Bill bill = new Bill();

        bill.setAppointment(appointment);
        bill.setDoctor(appointment.getDoctor());
        bill.setPatient(appointment.getPatient());
        bill.setAmount(500.0);
        bill.setPaymentStatus("Unpaid");

        return repository.save(bill);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
}