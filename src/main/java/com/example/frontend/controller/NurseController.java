package com.example.frontend.controller;

import com.example.frontend.service.NurseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("entity/arunima/Nurse")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService){
        this.nurseService = nurseService;
    }

    // Main Hub (The card grid)
    @GetMapping("")
    public String nursePage(){
        return "nurse/nurse";
    }

    // 1. Get All Nurses
    @GetMapping("/all")
    public String getAllNurses(Model model) {
        model.addAttribute("nurses", nurseService.getAllNurses());
        return "nurse/all-nurses";
    }

    // 2. Search by ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("nurseById", nurseService.getNurseById(id));
        }
        return "nurse/nurse-id";
    }

    // 3. Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("nurseByName", nurseService.getNurseByName(name));
        }
        return "nurse/nurse-name";
    }

    // 4. Search by Position
    @GetMapping("/searchByPosition")
    public String searchByPosition(@RequestParam(required = false) String position, Model model) {
        if (position != null && !position.isEmpty()) {
            model.addAttribute("nurseByPosition", nurseService.getNurseByPosition(position));
        }
        return "nurse/nurse-position";
    }

    // 5. Create Form & Action
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "nurse/nurse-create";
    }

    @PostMapping("/create")
    public String createNurse(@RequestParam Map<String, Object> nurse, RedirectAttributes redirectAttributes){
        if (nurse.get("registered") != null) {
            String val = nurse.get("registered").toString();
            nurse.put("registered", val.equalsIgnoreCase("true") ? 1 : 0);
        }
        nurseService.createNurse(nurse);
        redirectAttributes.addFlashAttribute("successMessage", "Nurse created successfully!");
        return "redirect:/entity/arunima/Nurse/createForm";
    }

    // 6. Update Form & Action
    @GetMapping("/updateFormPage")
    public String showUpdateForm() {
        return "nurse/nurse-update";
    }

    @PostMapping("/updateForm")
    public String processUpdate(@RequestParam int id, @RequestParam Map<String, Object> body, RedirectAttributes redirectAttributes) {
        // Remove 'id' from the map so it doesn't get sent in the JSON body if your API doesn't want it there
        body.remove("id");
        nurseService.updateNurse(id, body);
        redirectAttributes.addFlashAttribute("successMessage", "Nurse ID " + id + " updated successfully!");
        return "redirect:/entity/arunima/Nurse/updateFormPage";
    }
}