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

    @GetMapping
    public String physicianPage(Model model){
        model.addAttribute("physicians", physicianService.getAllPhysicians());
        return "physician";
    }

    // Search by ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("physicians", physicianService.getAllPhysicians());
        model.addAttribute("physicianById", physicianService.getPhysicianById(id));
        return "physician";
    }

    // Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("physicians", physicianService.getAllPhysicians());
        model.addAttribute("physicianByName", physicianService.getPhysicianByName(name));
        return "physician";
    }

    // Search by Position
    @GetMapping("/searchByPosition")
    public String searchByPosition(@RequestParam String position, Model model) {
        model.addAttribute("physicians", physicianService.getAllPhysicians());
        model.addAttribute("physicianByPosition", physicianService.getPhysicianByPosition(position));
        return "physician";
    }

    // Create physician
    @PostMapping("/create")
    public String createPhysician(@RequestParam Map<String, Object> physician, RedirectAttributes redirectAttributes){
        Map result = physicianService.createPhysician(physician);
        if (result == null) {
            redirectAttributes.addFlashAttribute("createError", "Create failed. Please check inputs.");
        } else {
            redirectAttributes.addFlashAttribute("createSuccess", "Physician created successfully.");
        }
        return "redirect:/entity/ayan/Physician";
    }

    // Update form handler
    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int id,
                             @RequestParam Map<String, Object> body,
                             RedirectAttributes redirectAttributes) {
        body.remove("id");
        Map existing = physicianService.getPhysicianById(id);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("updateError", "Update failed: ID " + id + " not found.");
        } else {
            physicianService.updatePhysician(id, body);
            redirectAttributes.addFlashAttribute("updateSuccess", "Physician updated successfully.");
        }
        return "redirect:/entity/ayan/Physician";
    }

    // Delete physician handler
    @PostMapping("/delete/{id}")
    public String deletePhysician(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Map existing = physicianService.getPhysicianById(id);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("deleteError", "Delete failed: ID " + id + " not found.");
        } else {
            physicianService.deletePhysician(id);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Physician deleted successfully.");
        }
        return "redirect:/entity/ayan/Physician";
    }
}