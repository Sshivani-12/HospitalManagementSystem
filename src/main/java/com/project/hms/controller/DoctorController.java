package com.project.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.hms.entity.Doctor;
import com.project.hms.service.DoctorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    // VIEW ALL
    @GetMapping
    public String listDoctors(Model model) {
        model.addAttribute("doctors", service.getAll());
        return "doctor-list";
    }

    // SHOW ADD FORM
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor-form";
    }

    // SAVE (ADD + UPDATE)
    @PostMapping("/save")
    public String saveDoctor(@Valid @ModelAttribute Doctor doctor,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "doctor-form";
        }
        service.save(doctor);
        return "redirect:/doctors";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", service.getById(id));
        return "doctor-form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/doctors";
    }
}