package com.example.frontend.controller;

import com.example.frontend.service.StayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("entity/soumadwip/Stay")
public class StayController {

    private final StayService stayService;

    public StayController(StayService stayService){
        this.stayService = stayService;
    }

    @GetMapping("")
    public String stayHub() {
        return "stay/stay";
    }

    // 1. All Stays
    @GetMapping("/all")
    public String getAllStays(Model model) {
        model.addAttribute("stays", stayService.getAllStays());
        return "stay/all-stays";
    }

    // 2. Search by ID
    @GetMapping("/searchByIdPage")
    public String searchByIdPage() {
        return "stay/stay-id";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("stayById", stayService.getStayById(id));
        }
        return "stay/stay-id";
    }

    // 3. Search by Patient
    @GetMapping("/searchByPatientPage")
    public String searchByPatientPage() {
        return "stay/stay-patient";
    }

    @GetMapping("/searchByPatient")
    public String searchByPatient(@RequestParam(required = false) Integer patientId, Model model) {
        if (patientId != null) {
            model.addAttribute("stayByPatient", stayService.getStayByPatient(patientId));
        }
        return "stay/stay-patient";
    }

    // 4. Search by Room (ADDED THIS SECTION)
    @GetMapping("/searchByRoomPage")
    public String searchByRoomPage() {
        return "stay/stay-room";
    }

    @GetMapping("/searchByRoom")
    public String searchByRoom(@RequestParam(required = false) Integer roomId, Model model) {
        if (roomId != null) {
            model.addAttribute("stayByRoom", stayService.getStayByRoom(roomId));
        }
        return "stay/stay-room";
    }

    // 5. Create
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "stay/stay-create";
    }

    @PostMapping("/create")
    public String createStay(@RequestParam Map<String, Object> stay, RedirectAttributes ra) {
        stayService.createStay(stay);
        ra.addFlashAttribute("successMessage", "Stay record created successfully!");
        return "redirect:/entity/soumadwip/Stay/createForm";
    }

    // 6. Update
    @GetMapping("/updateFormPage")
    public String showUpdateForm() {
        return "stay/stay-update";
    }

    @PostMapping("/updateForm")
    public String processUpdate(@RequestParam int id, @RequestParam Map<String, Object> body, RedirectAttributes ra) {
        body.remove("id");
        stayService.updateStay(id, body);
        ra.addFlashAttribute("successMessage", "Stay ID " + id + " updated successfully!");
        return "redirect:/entity/soumadwip/Stay/updateFormPage";
    }
}