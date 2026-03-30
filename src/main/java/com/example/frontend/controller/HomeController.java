package com.example.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        List<Map<String, Object>> members = new ArrayList<>();

        // Member 1
        Map<String, Object> m1 = new LinkedHashMap<>();
        m1.put("id", "ayan");
        m1.put("name", "Ayan Pal");
        m1.put("initials", "AP");
        m1.put("entities", Arrays.asList("Physician", "Department", "Affiliated With"));
        members.add(m1);

        // Member 2
        Map<String, Object> m2 = new LinkedHashMap<>();
        m2.put("id", "arunima");
        m2.put("name", "Arunima Bhattacharyya");
        m2.put("initials", "AB");
        m2.put("entities", Arrays.asList("Nurse", "On-Call", "Medication"));
        members.add(m2);

        // Member 3
        Map<String, Object> m3 = new LinkedHashMap<>();
        m3.put("id", "soumadwip");
        m3.put("name", "Soumadwip Ghara");
        m3.put("initials", "SG");
        m3.put("entities", Arrays.asList("Block", "Room", "Stay"));
        members.add(m3);

        // Member 4
        Map<String, Object> m4 = new LinkedHashMap<>();
        m4.put("id", "bidwattam");
        m4.put("name", "Bidwattam Datta");
        m4.put("initials", "BD");
        m4.put("entities", Arrays.asList("Procedures", "TrainedIn", "Undergoes"));
        members.add(m4);

        // Member 5
        Map<String, Object> m5 = new LinkedHashMap<>();
        m5.put("id", "anubhob");
        m5.put("name", "Anubhob Dey");
        m5.put("initials", "AD");
        m5.put("entities", Arrays.asList("Patient", "Appointment", "Prescribes"));
        members.add(m5);

        model.addAttribute("members", members);
        return "home";
    }
}