package com.example.frontend.controller;

import com.example.frontend.service.UndergoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UndergoesController {

    @Autowired
    private UndergoesService undergoesService;

    @GetMapping("/entity/bidwattam/Undergoes")
    public String index() {
        return "undergoes/undergoes";
    }

    @GetMapping("/entity/bidwattam/Undergoes/all")
    public String getAll(Model model) {
        model.addAttribute("undergoesList", undergoesService.getAll());
        return "undergoes/all-undergoes";
    }

    @GetMapping("/entity/bidwattam/Undergoes/searchById")
    public String searchById(
            @RequestParam(required = false) Integer patient,
            @RequestParam(required = false) Integer procedure,
            @RequestParam(required = false) Integer stay,
            @RequestParam(required = false) String date,
            Model model) {

        if (patient != null && procedure != null && stay != null && date != null && !date.isBlank()) {
            model.addAttribute("undergoesById", undergoesService.getById(patient, procedure, stay, date));
        }
        return "undergoes/undergoes-id";
    }

    @GetMapping("/entity/bidwattam/Undergoes/searchByPatient")
    public String searchByPatient(@RequestParam(required = false) Integer patientId, Model model) {
        if (patientId != null) {
            model.addAttribute("undergoesByPatient", undergoesService.getByPatient(patientId));
        }
        return "undergoes/undergoes-patient";
    }

    @GetMapping("/entity/bidwattam/Undergoes/searchByProcedure")
    public String searchByProcedure(@RequestParam(required = false) Integer procedureId, Model model) {
        if (procedureId != null) {
            model.addAttribute("undergoesByProcedure", undergoesService.getByProcedure(procedureId));
        }
        return "undergoes/undergoes-procedure";
    }

    @GetMapping("/entity/bidwattam/Undergoes/searchByStay")
    public String searchByStay(@RequestParam(required = false) Integer stayId, Model model) {
        if (stayId != null) {
            model.addAttribute("undergoesByStay", undergoesService.getByStay(stayId));
        }
        return "undergoes/undergoes-stay";
    }

    @GetMapping("/entity/bidwattam/Undergoes/searchByDateRange")
    public String searchByDateRange(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            Model model) {

        if (start != null && !start.isBlank() && end != null && !end.isBlank()) {
            model.addAttribute("undergoesByDateRange", undergoesService.getByDateRange(start, end));
        }
        return "undergoes/undergoes-daterange";
    }

    @GetMapping("/entity/bidwattam/Undergoes/createForm")
    public String createForm() {
        return "undergoes/undergoes-create";
    }

    @PostMapping("/entity/bidwattam/Undergoes/create")
    public String create(
            @RequestParam int patient,
            @RequestParam int procedure,
            @RequestParam int stay,
            @RequestParam String dateUndergoes,
            @RequestParam int physician,
            @RequestParam int assistingNurse,
            Model model) {

        Map<String, Object> body = new HashMap<>();
        body.put("patient", patient);
        body.put("procedure", procedure);
        body.put("stay", stay);
        body.put("dateUndergoes", dateUndergoes);
        body.put("physician", physician);
        body.put("assistingNurse", assistingNurse);

        undergoesService.create(body);
        model.addAttribute("successMessage", "Undergoes record created successfully!");
        return "undergoes/undergoes-create";
    }

    @GetMapping("/entity/bidwattam/Undergoes/updateFormPage")
    public String updateFormPage() {
        return "undergoes/undergoes-update";
    }

    @PostMapping("/entity/bidwattam/Undergoes/updateForm")
    public String update(
            @RequestParam int patient,
            @RequestParam int procedure,
            @RequestParam int stay,
            @RequestParam String date,
            @RequestParam int physician,
            @RequestParam int assistingNurse,
            Model model) {

        Map<String, Object> body = new HashMap<>();
        body.put("patient", patient);
        body.put("procedure", procedure);
        body.put("stay", stay);
        body.put("dateUndergoes", date);
        body.put("physician", physician);
        body.put("assistingNurse", assistingNurse);

        undergoesService.update(patient, procedure, stay, date, body);
        model.addAttribute("successMessage", "Undergoes record updated successfully!");
        return "undergoes/undergoes-update";
    }
}