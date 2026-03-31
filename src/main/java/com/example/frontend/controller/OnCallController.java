package com.example.frontend.controller;

import com.example.frontend.service.OnCallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/entity/arunima/On-Call")
public class OnCallController {

    private final OnCallService onCallService;

    public OnCallController(OnCallService onCallService) {
        this.onCallService = onCallService;
    }

    @GetMapping("")
    public String mainPage() {
        return "oncall/oncall";
    }

    @GetMapping("/all")
    public String allOnCalls(Model model) {
        model.addAttribute("oncalls", onCallService.getAllOnCall());
        return "oncall/oncall-all";
    }

    @GetMapping("/searchByNurse")
    public String searchByNurse(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("oncallByNurse", onCallService.getByNurse(id));
        }
        return "oncall/oncall-nurse";
    }

    @GetMapping("/searchByFloor")
    public String searchByFloor(@RequestParam(required = false) Integer blockFloor, Model model) {
        if (blockFloor != null) {
            model.addAttribute("oncallByFloor", onCallService.getBlocksByFloor(blockFloor));
        }
        return "oncall/oncall-floor";
    }

    @GetMapping("/searchByCode")
    public String searchByCode(@RequestParam(required = false) Integer blockCode, Model model) {
        if (blockCode != null) {
            model.addAttribute("oncallByCode", onCallService.getBlocksByCode(blockCode));
        }
        return "oncall/oncall-code";
    }

    @GetMapping("/createForm")
    public String createForm() {
        return "oncall/oncall-create";
    }

    @PostMapping("/create")
    public String create(@RequestParam int nurseId,
                         @RequestParam int blockFloor,
                         @RequestParam int blockCode,
                         @RequestParam String startTime,
                         @RequestParam String endTime,
                         RedirectAttributes ra) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nurseId", nurseId);
        body.put("blockFloor", blockFloor);
        body.put("blockCode", blockCode);
        body.put("onCallStart", startTime);
        body.put("onCallEnd", endTime);

        onCallService.createOnCall(body);
        ra.addFlashAttribute("successMessage", "On-Call schedule created successfully for Nurse ID: " + nurseId);

        return "redirect:/entity/arunima/On-Call/createForm";
    }
}