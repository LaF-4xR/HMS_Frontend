package com.example.frontend.controller;

import com.example.frontend.service.TrainedInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TrainedInController {

    @Autowired
    private TrainedInService trainedInService;

    // ── Entry point from home page ──
    @GetMapping("/entity/bidwattam/TrainedIn")
    public String index() {
        return "trainedin/trainedin";
    }

    // ── GET ALL ──
    @GetMapping("/entity/bidwattam/TrainedIn/all")
    public String getAll(Model model) {
        model.addAttribute("trainedInList", trainedInService.getAll());
        return "trainedin/all-trainedin";
    }

    // ── GET BY COMPOSITE ID ──
    @GetMapping("/entity/bidwattam/TrainedIn/searchById")
    public String searchById(
            @RequestParam(required = false) Integer physician,
            @RequestParam(required = false) Integer treatment,
            Model model) {
        if (physician != null && treatment != null) {
            model.addAttribute("trainedInById", trainedInService.getById(physician, treatment));
        }
        return "trainedin/trainedin-id";
    }

    // ── GET BY PHYSICIAN ──
    @GetMapping("/entity/bidwattam/TrainedIn/searchByPhysician")
    public String searchByPhysician(
            @RequestParam(required = false) Integer physicianId,
            Model model) {
        if (physicianId != null) {
            model.addAttribute("trainedInByPhysician", trainedInService.getByPhysician(physicianId));
        }
        return "trainedin/trainedin-physician";
    }

    // ── GET BY TREATMENT ──
    @GetMapping("/entity/bidwattam/TrainedIn/searchByTreatment")
    public String searchByTreatment(
            @RequestParam(required = false) Integer treatmentId,
            Model model) {
        if (treatmentId != null) {
            model.addAttribute("trainedInByTreatment", trainedInService.getByTreatment(treatmentId));
        }
        return "trainedin/trainedin-treatment";
    }

    // ── CREATE FORM PAGE ──
    @GetMapping("/entity/bidwattam/TrainedIn/createForm")
    public String createForm() {
        return "trainedin/trainedin-create";
    }

    // ── CREATE ──
    @PostMapping("/entity/bidwattam/TrainedIn/create")
    public String create(
            @RequestParam int physician,
            @RequestParam int treatment,
            @RequestParam String certificationDate,
            @RequestParam String certificationExpires,
            Model model) {

        Map<String, Object> body = new HashMap<>();
        body.put("physicianId", physician);
        body.put("treatmentId", treatment);
        body.put("certificationDate", certificationDate);
        body.put("certificationExpires", certificationExpires);

        trainedInService.create(body);
        model.addAttribute("successMessage", "Training record created successfully!");
        return "trainedin/trainedin-create";
    }

    // ── UPDATE FORM PAGE ──
    @GetMapping("/entity/bidwattam/TrainedIn/updateFormPage")
    public String updateFormPage() {
        return "trainedin/trainedin-update";
    }

    // ── UPDATE ──
    @PostMapping("/entity/bidwattam/TrainedIn/updateForm")
    public String update(
            @RequestParam int physician,
            @RequestParam int treatment,
            @RequestParam String certificationDate,
            @RequestParam String certificationExpires,
            Model model) {

        Map<String, Object> body = new HashMap<>();
        body.put("physicianId", physician);
        body.put("treatmentId", treatment);
        body.put("certificationDate", certificationDate);
        body.put("certificationExpires", certificationExpires);

        trainedInService.update(physician, treatment, body);
        model.addAttribute("successMessage", "Training record updated successfully!");
        return "trainedin/trainedin-update";
    }
}