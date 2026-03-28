package com.example.frontend.controller;

import com.example.frontend.service.UndergoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UndergoesController {

    @Autowired
    private UndergoesService undergoesService;

    // ── Entry point from home page ──
    @GetMapping("/entity/bidwattam/Undergoes")
    public String index(Model model) {
        model.addAttribute("undergoesList", undergoesService.getAll());
        return "undergoes";
    }

    // ── GET BY COMPOSITE ID ──
    @GetMapping("/entity/bidwattam/Undergoes/searchById")
    public String searchById(
            @RequestParam int patient,
            @RequestParam int procedure,
            @RequestParam int stay,
            @RequestParam String date,
            Model model) {

        model.addAttribute("undergoesList", undergoesService.getAll());
        model.addAttribute("undergoesById",
                undergoesService.getById(patient, procedure, stay, date));
        return "undergoes";
    }

    // ── GET BY PATIENT ──
    @GetMapping("/entity/bidwattam/Undergoes/searchByPatient")
    public String searchByPatient(@RequestParam int patientId, Model model) {
        model.addAttribute("undergoesList", undergoesService.getAll());
        model.addAttribute("undergoesByPatient", undergoesService.getByPatient(patientId));
        return "undergoes";
    }

    // ── GET BY PROCEDURE ──
    @GetMapping("/entity/bidwattam/Undergoes/searchByProcedure")
    public String searchByProcedure(@RequestParam int procedureId, Model model) {
        model.addAttribute("undergoesList", undergoesService.getAll());
        model.addAttribute("undergoesByProcedure", undergoesService.getByProcedure(procedureId));
        return "undergoes";
    }

    // ── GET BY STAY ──
    @GetMapping("/entity/bidwattam/Undergoes/searchByStay")
    public String searchByStay(@RequestParam int stayId, Model model) {
        model.addAttribute("undergoesList", undergoesService.getAll());
        model.addAttribute("undergoesByStay", undergoesService.getByStay(stayId));
        return "undergoes";
    }

    // ── GET BY DATE RANGE ──
    @GetMapping("/entity/bidwattam/Undergoes/searchByDateRange")
    public String searchByDateRange(
            @RequestParam String start,
            @RequestParam String end,
            Model model) {

        model.addAttribute("undergoesList", undergoesService.getAll());
        model.addAttribute("undergoesByDateRange", undergoesService.getByDateRange(start, end));
        return "undergoes";
    }

    // ── CREATE ──
    @PostMapping("/entity/bidwattam/Undergoes/create")
    public String create(
            @RequestParam int patient,
            @RequestParam int procedure,
            @RequestParam int stay,
            @RequestParam String dateUndergoes,
            @RequestParam int physician,
            @RequestParam int assistingNurse) {

        Map<String, Object> body = new HashMap<>();
        body.put("patient", patient);
        body.put("procedure", procedure);
        body.put("stay", stay);
        body.put("dateUndergoes", dateUndergoes);
        body.put("physician", physician);
        body.put("assistingNurse", assistingNurse);

        undergoesService.create(body);
        return "redirect:/entity/bidwattam/Undergoes";
    }

    // ── UPDATE ──
    @PostMapping("/entity/bidwattam/Undergoes/updateForm")
    public String update(
            @RequestParam int patient,
            @RequestParam int procedure,
            @RequestParam int stay,
            @RequestParam String date,
            @RequestParam int physician,
            @RequestParam int assistingNurse) {

        Map<String, Object> body = new HashMap<>();
        body.put("patient", patient);
        body.put("procedure", procedure);
        body.put("stay", stay);
        body.put("dateUndergoes", date);
        body.put("physician", physician);
        body.put("assistingNurse", assistingNurse);

        undergoesService.update(patient, procedure, stay, date, body);
        return "redirect:/entity/bidwattam/Undergoes";
    }

    // ── DELETE ──
    @PostMapping("/entity/bidwattam/Undergoes/deleteForm")
    public String delete(
            @RequestParam int patient,
            @RequestParam int procedure,
            @RequestParam int stay,
            @RequestParam String date) {

        undergoesService.delete(patient, procedure, stay, date);
        return "redirect:/entity/bidwattam/Undergoes";
    }
}