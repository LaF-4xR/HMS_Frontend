package com.example.frontend.controller;

import com.example.frontend.service.OnCallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/entity/arunima/On-Call")
public class OnCallController {

    private final OnCallService onCallService;

    public OnCallController(OnCallService onCallService) {
        this.onCallService = onCallService;
    }

    // GET ALL
    @GetMapping
    public String page(Model model) {
        model.addAttribute("oncalls", onCallService.getAllOnCall());
        return "oncall";
    }

    // SEARCH BY NURSE
    @GetMapping("/searchByNurse")
    public String searchByNurse(@RequestParam int id, Model model) {
        model.addAttribute("oncalls", onCallService.getAllOnCall());
        model.addAttribute("oncallByNurse", onCallService.getByNurse(id));
        return "oncall";
    }

    //create
    @PostMapping("/create")
    public String create(@RequestParam int nurseId,
                         @RequestParam int blockFloor,   // ← from form now
                         @RequestParam int blockCode,    // ← from form now
                         @RequestParam String startTime,
                         @RequestParam String endTime) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nurseId", nurseId);
        body.put("blockFloor", blockFloor);
        body.put("blockCode", blockCode);
        body.put("onCallStart", startTime);
        body.put("onCallEnd", endTime);

        System.out.println("BODY: " + body);
        onCallService.createOnCall(body);

        return "redirect:/entity/arunima/On-Call";
    }
}