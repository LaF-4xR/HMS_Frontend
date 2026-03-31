package com.example.frontend.controller;

import com.example.frontend.service.PhysicianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("entity/ayan/Physician")
public class PhysicianController {

    private final PhysicianService physicianService;

    public PhysicianController(PhysicianService physicianService){
        this.physicianService = physicianService;
    }

    // Main Hub (The card grid)
    @GetMapping("")
    public String physicianPage(){
        return "physician/physician";
    }

    // 1. Get All Physicians
    @GetMapping("/all")
    public String getAllPhysicians(Model model) {
        model.addAttribute("physicians", physicianService.getAllPhysicians());
        return "physician/all-physicians";
    }

    // 2. Search by ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("physicianById", physicianService.getPhysicianById(id));
        }
        return "physician/physician-id";
    }

    // 3. Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("physicianByName", physicianService.getPhysicianByName(name));
        }
        return "physician/physician-name";
    }

    // 4. Search by Position
    @GetMapping("/searchByPosition")
    public String searchByPosition(@RequestParam(required = false) String position, Model model) {
        if (position != null && !position.isEmpty()) {
            model.addAttribute("physicianByPosition", physicianService.getPhysicianByPosition(position));
        }
        return "physician/physician-position";
    }

    // 5. Create Form & Action
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "physician/physician-create";
    }

    @PostMapping("/create")
    public String createPhysician(@RequestParam Map<String, Object> physician, RedirectAttributes redirectAttributes){
        Map result = physicianService.createPhysician(physician);
        if (result == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Create failed. Please check inputs.");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Physician created successfully!");
        }
        return "redirect:/entity/ayan/Physician/createForm";
    }

    // 6. Update Form & Action
    @GetMapping("/updateFormPage")
    public String showUpdateForm() {
        return "physician/physician-update";
    }

    @PostMapping("/updateForm")
    public String processUpdate(@RequestParam int id, @RequestParam Map<String, Object> body, RedirectAttributes redirectAttributes) {
        body.remove("id");
        Map existing = physicianService.getPhysicianById(id);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Update failed: ID " + id + " not found.");
        } else {
            physicianService.updatePhysician(id, body);
            redirectAttributes.addFlashAttribute("successMessage", "Physician ID " + id + " updated successfully!");
        }
        return "redirect:/entity/ayan/Physician/updateFormPage";
    }
}