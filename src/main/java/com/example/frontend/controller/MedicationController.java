package com.example.frontend.controller;

import com.example.frontend.service.MedicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/entity/arunima/Medication")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping("")
    public String medicationPage() {
        return "medication/medication";
    }

    // VIEW ALL
    @GetMapping("/all")
    public String allMedications(Model model) {
        model.addAttribute("medications", medicationService.getAllMedication());
        return "medication/medication-all";
    }

    // SEARCH BY CODE
    @GetMapping("/searchByCode")
    public String searchByCode(@RequestParam(required = false) Integer code, Model model) {
        if (code != null) {
            model.addAttribute("medicationByCode", medicationService.getMedicationByCode(code));
        }
        return "medication/medication-code";
    }

    // SEARCH BY NAME
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("medicationByName", medicationService.getMedicationByName(name));
        }
        return "medication/medication-name";
    }

    // SEARCH BY BRAND
    @GetMapping("/searchByBrand")
    public String searchByBrand(@RequestParam(required = false) String brand, Model model) {
        if (brand != null && !brand.isEmpty()) {
            model.addAttribute("medicationByBrand", medicationService.getMedicationByBrand(brand));
        }
        return "medication/medication-brand";
    }

    // CREATE FORM
    @GetMapping("/createForm")
    public String createForm() {
        return "medication/medication-create";
    }

    @PostMapping("/create")
    public String createMedication(@RequestParam Map<String, Object> medication, RedirectAttributes ra){
        medicationService.createMedication(medication);
        ra.addFlashAttribute("successMessage", "Medication added to inventory successfully.");
        return "redirect:/entity/arunima/Medication/createForm";
    }

    // UPDATE FORM
    @GetMapping("/updateFormPage")
    public String updateFormPage() {
        return "medication/medication-update";
    }

    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int code, @RequestParam Map<String, Object> body, RedirectAttributes ra) {
        body.remove("code");
        medicationService.updateMedication(code, body);
        ra.addFlashAttribute("successMessage", "Medication code " + code + " has been updated.");
        return "redirect:/entity/arunima/Medication/updateFormPage";
    }
}