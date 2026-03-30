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
    public String index(Model model) {
        model.addAttribute("procedureList", procedureService.getAll());
        return "procedures";
    }

    // ── GET BY ID ──
    @GetMapping("/entity/bidwattam/Procedures/searchById")
    public String searchById(@RequestParam int code, Model model) {
        model.addAttribute("procedureList", procedureService.getAll());
        model.addAttribute("procedureById", procedureService.getById(code));
        return "procedures";
    }

    // ── SEARCH BY NAME ──
    @GetMapping("/entity/bidwattam/Procedures/searchByName")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("procedureList", procedureService.getAll());
        model.addAttribute("procedureByName", procedureService.searchByName(name));
        return "procedures";
    }

    // ── FILTER BY COST ──
    @GetMapping("/entity/bidwattam/Procedures/filterByCost")
    public String filterByCost(
            @RequestParam double min,
            @RequestParam double max,
            Model model) {
        model.addAttribute("procedureList", procedureService.getAll());
        model.addAttribute("procedureByCost", procedureService.filterByCost(min, max));
        return "procedures";
    }

    // ── CREATE ──
    @PostMapping("/entity/bidwattam/Procedures/create")
    public String create(
            @RequestParam int code,
            @RequestParam String name,
            @RequestParam double cost) {

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("name", name);
        body.put("cost", cost);

        procedureService.create(body);
        return "redirect:/entity/bidwattam/Procedures";
    }

    // ── UPDATE ──
    @PostMapping("/entity/bidwattam/Procedures/updateForm")
    public String update(
            @RequestParam int code,
            @RequestParam String name,
            @RequestParam double cost) {

        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("name", name);
        body.put("cost", cost);

        procedureService.update(code, body);
        return "redirect:/entity/bidwattam/Procedures";
    }

    // ── DELETE ──
    @PostMapping("/entity/bidwattam/Procedures/deleteForm")
    public String delete(@RequestParam int code) {
        procedureService.delete(code);
        return "redirect:/entity/bidwattam/Procedures";
    }
}