package com.project.hms.entity;

import jakarta.persistence.*;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    @OneToOne
    private Appointment appointment;

    private Double amount;

    private String paymentStatus; 

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() { return doctor; }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Appointment getAppointment() { return appointment; }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Double getAmount() { return amount; }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() { return paymentStatus; }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}