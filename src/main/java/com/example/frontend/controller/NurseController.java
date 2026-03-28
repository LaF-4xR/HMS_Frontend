package com.example.frontend.controller;

import com.example.frontend.service.NurseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("entity/arunima/Nurse")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService){
        this.nurseService = nurseService;
    }

    @GetMapping
    public String nursePage(Model model){
        model.addAttribute("nurses", nurseService.getAllNurses());
        return "nurse";
    }

    // Search by ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("nurses", nurseService.getAllNurses());
        model.addAttribute("nurseById", nurseService.getNurseById(id));
        return "nurse";
    }

    // Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("nurses", nurseService.getAllNurses());
        model.addAttribute("nurseByName", nurseService.getNurseByName(name));
        return "nurse";
    }

    // Search by Position
    @GetMapping("/searchByPosition")
    public String searchByPosition(@RequestParam String position, Model model) {
        model.addAttribute("nurses", nurseService.getAllNurses());
        model.addAttribute("nurseByPosition", nurseService.getNurseByPosition(position));
        return "nurse";
    }

    // Create nurse
    @PostMapping("/create")
    public String createNurse(@RequestParam Map<String, Object> nurse){

        if (nurse.get("registered") != null) {
            String val = nurse.get("registered").toString();
            nurse.put("registered", val.equalsIgnoreCase("true") ? 1 : 0);
        }

        nurseService.createNurse(nurse);
        return "redirect:/entity/arunima/Nurse";
    }

    // Update nurse
    @PostMapping("/update/{id}")
    public String updateNurse(@PathVariable int id, @RequestParam Map<String, Object> nurse){
        nurseService.updateNurse(id, nurse);
        return "redirect:/entity/arunima/Nurse";
    }

    // Update form handler
    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int id,
                             @RequestParam Map<String, Object> body) {
        body.remove("id");
        nurseService.updateNurse(id, body);
        return "redirect:/entity/arunima/Nurse";
    }
}