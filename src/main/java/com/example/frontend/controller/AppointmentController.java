package com.example.frontend.controller;

import com.example.frontend.service.AppointmentService;
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
@RequestMapping("entity/anubhob/Appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String appointmentPage() {
        return "appointment/appointment";
    }

    @GetMapping("/all")
    public String allAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointment/appointment-all";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("appointmentById", appointmentService.getAppointmentById(id));
        }
        return "appointment/appointment-id";
    }

    @GetMapping("/searchByPatient")
    public String searchByPatient(@RequestParam(required = false) Integer patientSsn, Model model) {
        if (patientSsn != null) {
            model.addAttribute("appointmentByPatient", appointmentService.getByPatientSsn(patientSsn));
        }
        return "appointment/appointment-patient";
    }

    @GetMapping("/searchByNurse")
    public String searchByNurse(@RequestParam(required = false) Integer nurseId, Model model) {
        if (nurseId != null) {
            model.addAttribute("appointmentByNurse", appointmentService.getByNurseId(nurseId));
        }
        return "appointment/appointment-nurse";
    }

    @GetMapping("/searchByPhysician")
    public String searchByPhysician(@RequestParam(required = false) Integer physicianId, Model model) {
        if (physicianId != null) {
            model.addAttribute("appointmentByPhysician", appointmentService.getByPhysicianId(physicianId));
        }
        return "appointment/appointment-physician";
    }

    @GetMapping("/createForm")
    public String createForm() {
        return "appointment/appointment-create";
    }

    @GetMapping("/updateFormPage")
    public String updateFormPage() {
        return "appointment/appointment-update";
    }

    @PostMapping("/create")
    public String createAppointment(@RequestParam int appointmentId,
                                    @RequestParam int patientSsn,
                                    @RequestParam(required = false) String prepNurseId,
                                    @RequestParam int physicianId,
                                    @RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam String examinationRoom,
                                    RedirectAttributes redirectAttributes) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("appointmentId", appointmentId);
        body.put("patientSsn", patientSsn);

        Integer parsedPrepNurse = parseNullableInteger(prepNurseId);
        if (parsedPrepNurse != null) {
            body.put("prepNurseId", parsedPrepNurse);
        }

        body.put("physicianId", physicianId);
        body.put("start", start.trim());
        body.put("end", end.trim());
        body.put("examinationRoom", examinationRoom.trim());

        appointmentService.createAppointment(body);
        redirectAttributes.addFlashAttribute("successMessage", "Appointment created successfully.");
        return "redirect:/entity/anubhob/Appointment/createForm";
    }

    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int id,
                             @RequestParam int patientSsn,
                             @RequestParam(required = false) String prepNurseId,
                             @RequestParam int physicianId,
                             @RequestParam String start,
                             @RequestParam String end,
                             @RequestParam String examinationRoom,
                             RedirectAttributes redirectAttributes) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("appointmentId", id);
        body.put("patientSsn", patientSsn);

        Integer parsedPrepNurse = parseNullableInteger(prepNurseId);
        if (parsedPrepNurse != null) {
            body.put("prepNurseId", parsedPrepNurse);
        }

        body.put("physicianId", physicianId);
        body.put("start", start.trim());
        body.put("end", end.trim());
        body.put("examinationRoom", examinationRoom.trim());

        appointmentService.updateAppointment(id, body);
        redirectAttributes.addFlashAttribute("successMessage", "Appointment " + id + " updated successfully.");
        return "redirect:/entity/anubhob/Appointment/updateFormPage";
    }

    @PostMapping("/delete")
    public String deleteAppointment(@RequestParam int id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/entity/anubhob/Appointment";
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

