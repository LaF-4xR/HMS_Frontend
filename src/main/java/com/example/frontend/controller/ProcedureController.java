package com.example.frontend.controller;

import com.example.frontend.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    // ── Entry point from home page ──
    @GetMapping("/entity/bidwattam/Procedures")
    public String index() {
        return "procedure/procedure";
    }

    // ── GET ALL ──
    @GetMapping("/entity/bidwattam/Procedures/all")
    public String getAll(Model model) {
        model.addAttribute("procedureList", procedureService.getAll());
        return "procedure/all-procedures";
    }

    // ── GET BY CODE ──
    @GetMapping("/entity/bidwattam/Procedures/searchById")
    public String searchById(@RequestParam(required = false) Integer code, Model model) {
        if (code != null) {
            model.addAttribute("procedureById", procedureService.getById(code));
        }
        return "procedure/procedure-id";
    }

    // ── SEARCH BY NAME ──
    @GetMapping("/entity/bidwattam/Procedures/searchByName")
    public String searchByName(@RequestParam(required = false) String name, Model model) {
        if (name != null) {
            model.addAttribute("procedureByName", procedureService.searchByName(name));
        }
        return "procedure/procedure-name";
    }

    // ── FILTER BY COST ──
    @GetMapping("/entity/bidwattam/Procedures/filterByCost")
    public String filterByCost(
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            Model model) {
        if (min != null && max != null) {
            model.addAttribute("procedureByCost", procedureService.filterByCost(min, max));
        }
        return "procedure/procedure-cost";
    }

    // ── CREATE FORM PAGE ──
    @GetMapping("/entity/bidwattam/Procedures/createForm")
    public String createForm() {
        return "procedure/procedure-create";
    }

    // ── CREATE ──
    @PostMapping("/entity/bidwattam/Procedures/create")
    public String create(
            @RequestParam int code,
            @RequestParam String name,
            @RequestParam double cost,
            Model model) {

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("name", name);
        body.put("cost", cost);

        procedureService.create(body);
        model.addAttribute("successMessage", "Procedure created successfully!");
        return "procedure/procedure-create";
    }

    // ── UPDATE FORM PAGE ──
    @GetMapping("/entity/bidwattam/Procedures/updateFormPage")
    public String updateFormPage() {
        return "procedure/procedure-update";
    }

    // ── UPDATE ──
    @PostMapping("/entity/bidwattam/Procedures/updateForm")
    public String update(
            @RequestParam int code,
            @RequestParam String name,
            @RequestParam double cost,
            Model model) {

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("name", name);
        body.put("cost", cost);

        procedureService.update(code, body);
        model.addAttribute("successMessage", "Procedure updated successfully!");
        return "procedure/procedure-update";
    }
}