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
    public String index(Model model) {
        model.addAttribute("trainedInList", trainedInService.getAll());
        return "trainedin";
    }

    // ── GET BY COMPOSITE ID ──
    @GetMapping("/entity/bidwattam/TrainedIn/searchById")
    public String searchById(
            @RequestParam int physician,
            @RequestParam int treatment,
            Model model) {

        model.addAttribute("trainedInList", trainedInService.getAll());
        model.addAttribute("trainedInById",
                trainedInService.getById(physician, treatment));
        return "trainedin";
    }

    // ── GET BY PHYSICIAN ──
    @GetMapping("/entity/bidwattam/TrainedIn/searchByPhysician")
    public String searchByPhysician(@RequestParam int physicianId, Model model) {
        model.addAttribute("trainedInList", trainedInService.getAll());
        model.addAttribute("trainedInByPhysician",
                trainedInService.getByPhysician(physicianId));
        return "trainedin";
    }

    // ── GET BY TREATMENT ──
    @GetMapping("/entity/bidwattam/TrainedIn/searchByTreatment")
    public String searchByTreatment(@RequestParam int treatmentId, Model model) {
        model.addAttribute("trainedInList", trainedInService.getAll());
        model.addAttribute("trainedInByTreatment",
                trainedInService.getByTreatment(treatmentId));
        return "trainedin";
    }

    // ── CREATE ──
    @PostMapping("/entity/bidwattam/TrainedIn/create")
    public String create(
            @RequestParam int physician,
            @RequestParam int treatment,
            @RequestParam String certificationDate,
            @RequestParam String certificationExpires) {

        Map<String, Object> body = new HashMap<>();
        body.put("physicianId", physician);       // ← fixed
        body.put("treatmentId", treatment);       // ← fixed
        body.put("certificationDate", certificationDate);
        body.put("certificationExpires", certificationExpires);

        trainedInService.create(body);
        return "redirect:/entity/bidwattam/TrainedIn";
    }

    // ── UPDATE ──
    @PostMapping("/entity/bidwattam/TrainedIn/updateForm")
    public String update(
            @RequestParam int physician,
            @RequestParam int treatment,
            @RequestParam String certificationDate,
            @RequestParam String certificationExpires) {

        Map<String, Object> body = new HashMap<>();
        body.put("physicianId", physician);       // ← fixed
        body.put("treatmentId", treatment);       // ← fixed
        body.put("certificationDate", certificationDate);
        body.put("certificationExpires", certificationExpires);

        trainedInService.update(physician, treatment, body);
        return "redirect:/entity/bidwattam/TrainedIn";
    }

    // ── DELETE ──
    @PostMapping("/entity/bidwattam/TrainedIn/deleteForm")
    public String delete(
            @RequestParam int physician,
            @RequestParam int treatment) {

        trainedInService.delete(physician, treatment);
        return "redirect:/entity/bidwattam/TrainedIn";
    }
}