package com.example.frontend.controller;

import com.example.frontend.service.PrescribesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String prescribesPage() {
        return "prescribes/prescribes";
    }

    @GetMapping("/all")
    public String allPrescribes(Model model) {
        model.addAttribute("prescribes", prescribesService.getAllPrescribes());
        return "prescribes/prescribes-all";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int physicianId,
                             @RequestParam int patientSsn,
                             @RequestParam int medicationCode,
                             @RequestParam String date,
                             Model model) {
        model.addAttribute("prescribesById", prescribesService.getById(
                physicianId,
                patientSsn,
                medicationCode,
                normalizeDateTime(date)
        ));
        return "prescribes/prescribes-id";
    }

    @GetMapping("/searchByPhysician")
    public String searchByPhysician(@RequestParam int physicianId, Model model) {
        model.addAttribute("prescribesByPhysician", prescribesService.getByPhysician(physicianId));
        return "prescribes/prescribes-physician";
    }

    @GetMapping("/searchByPatient")
    public String searchByPatient(@RequestParam int patientSsn, Model model) {
        model.addAttribute("prescribesByPatient", prescribesService.getByPatient(patientSsn));
        return "prescribes/prescribes-patient";
    }

    @GetMapping("/searchByMedication")
    public String searchByMedication(@RequestParam int medicationCode, Model model) {
        model.addAttribute("prescribesByMedication", prescribesService.getByMedication(medicationCode));
        return "prescribes/prescribes-medication";
    }

    @GetMapping("/searchByAppointment")
    public String searchByAppointment(@RequestParam int appointmentId, Model model) {
        model.addAttribute("prescribesByAppointment", prescribesService.getByAppointment(appointmentId));
        return "prescribes/prescribes-appointment";
    }

    @GetMapping("/search")
    public String search(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         Model model) {
        model.addAttribute("prescribesBySearch", prescribesService.search(physicianId, patientSsn, medicationCode));
        return "prescribes/prescribes-search";
    }

    @GetMapping("/createForm")
    public String createForm() {
        return "prescribes/prescribes-create";
    }

    @GetMapping("/updateFormPage")
    public String updateFormPage() {
        return "prescribes/prescribes-update";
    }

    @PostMapping("/create")
    public String create(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         @RequestParam String date,
                         @RequestParam(required = false) String appointmentId,
                         @RequestParam String dose,
                         RedirectAttributes redirectAttributes) {

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
        redirectAttributes.addFlashAttribute("successMessage", "Prescription created successfully.");
        return "redirect:/entity/anubhob/Prescribes/createForm";
    }

    @PostMapping("/updateForm")
    public String update(@RequestParam int physicianId,
                         @RequestParam int patientSsn,
                         @RequestParam int medicationCode,
                         @RequestParam String date,
                         @RequestParam(required = false) String newMedicationCode,
                         @RequestParam(required = false) String appointmentId,
                         @RequestParam String dose,
                         RedirectAttributes redirectAttributes) {

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
        redirectAttributes.addFlashAttribute("successMessage", "Prescription updated successfully.");
        return "redirect:/entity/anubhob/Prescribes/updateFormPage";
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

