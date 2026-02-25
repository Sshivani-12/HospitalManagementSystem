package com.project.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.hms.entity.Patient;
import com.project.hms.service.PatientService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // VIEW ALL
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", service.getAll());
        return "patient-list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient-form";
    }
 
    // SAVE PATIENT
    @PostMapping("/save")
    public String savePatient(
            @Valid @ModelAttribute("patient") Patient patient,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "patient-form"; 
        }

        service.save(patient);
        return "redirect:/patients";
    }    
    

    // EDIT 
    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = service.getById(id);
        model.addAttribute("patient", patient);
        return "patient-form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/patients";
    }
}