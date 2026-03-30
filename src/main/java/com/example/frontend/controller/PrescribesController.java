package com.example.frontend.controller;

import com.example.frontend.service.PrescribesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("entity/anubhob/Prescribes")
public class PrescribesController {

    private final PrescribesService prescribesService;

    public PrescribesController(PrescribesService prescribesService) {
        this.prescribesService = prescribesService;
    }

    @GetMapping
    public String prescribesPage(Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        return "prescribes";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int physicianId,
                             @RequestParam int patientSsn,
                             @RequestParam int medicationCode,
                             @RequestParam String date,
                             Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        model.addAttribute("prescribesById", prescribesService.getById(
                physicianId,
                patientSsn,
                medicationCode,
                normalizeDateTime(date)
        ));
        return "prescribes";
    }

    @GetMapping("/searchByPhysician")
    public String searchByPhysician(@RequestParam int physicianId, Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        model.addAttribute("prescribesByPhysician", prescribesService.getByPhysician(physicianId));
        return "prescribes";
    }

    @GetMapping("/searchByPatient")
    public String searchByPatient(@RequestParam int patientSsn, Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        model.addAttribute("prescribesByPatient", prescribesService.getByPatient(patientSsn));
        return "prescribes";
    }

    @GetMapping("/searchByMedication")
    public String searchByMedication(@RequestParam int medicationCode, Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        model.addAttribute("prescribesByMedication", prescribesService.getByMedication(medicationCode));
        return "prescribes";
    }

    @GetMapping("/searchByAppointment")
    public String searchByAppointment(@RequestParam int appointmentId, Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        model.addAttribute("prescribesByAppointment", prescribesService.getByAppointment(appointmentId));
        return "prescribes";
    }

    @GetMapping("/search")
    public String search(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        model.addAttribute("prescribesBySearch", prescribesService.search(physicianId, patientSsn, medicationCode));
        return "prescribes";
    }

    @PostMapping("/create")
    public String create(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         @RequestParam String date,
                         @RequestParam(required = false) String appointmentId,
                         @RequestParam String dose) {

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("physicianId", physicianId);
        payload.put("patientSsn", patientSsn);
        payload.put("medicationCode", medicationCode);
        payload.put("date", normalizeDateTime(date));

        Integer parsedAppointmentId = parseNullableInteger(appointmentId);
        if (parsedAppointmentId != null) {
            payload.put("appointmentId", parsedAppointmentId);
        }

        payload.put("dose", dose.trim());

        prescribesService.createPrescribes(payload);
        return "redirect:/entity/anubhob/Prescribes";
    }

    @PostMapping("/updateForm")
    public String update(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         @RequestParam String date,
                         @RequestParam(required = false) String newMedicationCode,
                         @RequestParam(required = false) String appointmentId,
                         @RequestParam String dose) {

        Map<String, Object> payload = new LinkedHashMap<>();
        Integer parsedNewMedicationCode = parseNullableInteger(newMedicationCode);
        if (parsedNewMedicationCode != null) {
            payload.put("medicationCode", parsedNewMedicationCode);
        }

        Integer parsedAppointmentId = parseNullableInteger(appointmentId);
        if (parsedAppointmentId != null) {
            payload.put("appointmentId", parsedAppointmentId);
        }
        payload.put("dose", dose.trim());

        prescribesService.updatePrescribes(
                physicianId,
                patientSsn,
                medicationCode,
                normalizeDateTime(date),
                payload
        );

        return "redirect:/entity/anubhob/Prescribes";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         @RequestParam String date) {
        prescribesService.deletePrescribes(physicianId, patientSsn, medicationCode, normalizeDateTime(date));
        return "redirect:/entity/anubhob/Prescribes";
    }

    private String normalizeDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        String trimmed = dateTime.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }

        // datetime-local commonly submits without seconds; backend LocalDateTime.parse supports full ISO.
        if (trimmed.length() == 16) {
            return trimmed + ":00";
        }
        return trimmed;
    }

    private Integer parseNullableInteger(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

