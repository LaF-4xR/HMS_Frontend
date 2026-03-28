package com.example.frontend.controller;

import com.example.frontend.service.MedicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/entity/arunima/Medication")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping
    public String MedicationPage(Model model){
        model.addAttribute("medications", medicationService.getAllMedication());
        return "medication";
    }

    // Search by Code
    @GetMapping("/searchByCode")
    public String searchByCode(@RequestParam int code, Model model) {
        model.addAttribute("medications", medicationService.getAllMedication());
        model.addAttribute("medicationByCode", medicationService.getMedicationByCode(code));
        return "medication";
    }

    // Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("medications", medicationService.getAllMedication());
        model.addAttribute("medicationByName", medicationService.getMedicationByName(name));
        return "medication";
    }

    //search by brand
    @GetMapping("/searchByBrand")
    public String searchByBrand(@RequestParam String brand, Model model) {
        model.addAttribute("medications", medicationService.getAllMedication());
        model.addAttribute("medicationByBrand", medicationService.getMedicationByBrand(brand));
        return "medication";
    }

    // Create medication
    @PostMapping("/create")
    public String createMedication(@RequestParam Map<String, Object> medication){

//        if (medication.get("registered") != null) {
//            String val = medication.get("registered").toString();
//            medication.put("registered", val.equalsIgnoreCase("true") ? 1 : 0);
//        }

        medicationService.createMedication(medication);
        return "redirect:/entity/arunima/Medication";
    }

    // Update medication
    @PostMapping("/update/{code}")
    public String updateMedication(@PathVariable int code, @RequestParam Map<String, Object> medication){
        medicationService.updateMedication(code, medication);
        return "redirect:/entity/arunima/Medication";
    }

    // Update form handler
    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int code,
                             @RequestParam Map<String, Object> body) {
        body.remove("code");
        medicationService.updateMedication(code, body);
        return "redirect:/entity/arunima/Medication";
    }
}
