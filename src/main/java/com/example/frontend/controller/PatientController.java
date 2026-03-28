package com.example.frontend.controller;

import com.example.frontend.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("entity/anubhob/Patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String patientPage(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient";
    }

    @GetMapping("/searchBySsn")
    public String searchBySsn(@RequestParam int ssn, Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("patientBySsn", patientService.getPatientBySsn(ssn));
        return "patient";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("patientByName", patientService.getPatientByName(name));
        return "patient";
    }

    @GetMapping("/searchByPcp")
    public String searchByPcp(@RequestParam Integer pcp, Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("patientByPcp", patientService.getPatientByPcp(pcp));
        return "patient";
    }

    @PostMapping("/create")
    public String createPatient(@RequestParam int ssn,
                                @RequestParam String name,
                                @RequestParam String address,
                                @RequestParam String phone,
                                @RequestParam Long insuranceId,
                                @RequestParam Integer pcp) {
        Map<String, Object> patient = new LinkedHashMap<>();
        patient.put("ssn", ssn);
        patient.put("name", name.trim());
        patient.put("address", address.trim());
        patient.put("phone", phone.trim());
        patient.put("insuranceId", insuranceId);
        patient.put("pcp", pcp);

        patientService.createPatient(patient);
        return "redirect:/entity/anubhob/Patient";
    }

    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int ssn,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String address,
                             @RequestParam(required = false) String phone,
                             @RequestParam(required = false) Long insuranceId,
                             @RequestParam(required = false) Integer pcp) {
        Map<String, Object> body = new LinkedHashMap<>();
        if (name != null && !name.trim().isEmpty()) {
            body.put("name", name.trim());
        }
        if (address != null && !address.trim().isEmpty()) {
            body.put("address", address.trim());
        }
        if (phone != null && !phone.trim().isEmpty()) {
            body.put("phone", phone.trim());
        }
        if (insuranceId != null) {
            body.put("insuranceId", insuranceId);
        }
        if (pcp != null) {
            body.put("pcp", pcp);
        }

        patientService.updatePatient(ssn, body);
        return "redirect:/entity/anubhob/Patient";
    }

    @PostMapping("/delete")
    public String deletePatient(@RequestParam int ssn) {
        patientService.deletePatient(ssn);
        return "redirect:/entity/anubhob/Patient";
    }
}

