package com.example.frontend.controller;

import com.example.frontend.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String appointmentPage(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointment";
    }

    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("appointmentById", appointmentService.getAppointmentById(id));
        return "appointment";
    }

    @GetMapping("/searchByPatient")
    public String searchByPatient(@RequestParam int patientSsn, Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("appointmentByPatient", appointmentService.getByPatientSsn(patientSsn));
        return "appointment";
    }

    @GetMapping("/searchByNurse")
    public String searchByNurse(@RequestParam int nurseId, Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("appointmentByNurse", appointmentService.getByNurseId(nurseId));
        return "appointment";
    }

    @GetMapping("/searchByPhysician")
    public String searchByPhysician(@RequestParam int physicianId, Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("appointmentByPhysician", appointmentService.getByPhysicianId(physicianId));
        return "appointment";
    }

    @PostMapping("/create")
    public String createAppointment(@RequestParam int appointmentId,
                                    @RequestParam int patientSsn,
                                    @RequestParam(required = false) String prepNurseId,
                                    @RequestParam int physicianId,
                                    @RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam String examinationRoom) {
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
        return "redirect:/entity/anubhob/Appointment";
    }

    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int id,
                             @RequestParam int patientSsn,
                             @RequestParam(required = false) String prepNurseId,
                             @RequestParam int physicianId,
                             @RequestParam String start,
                             @RequestParam String end,
                             @RequestParam String examinationRoom) {
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
        return "redirect:/entity/anubhob/Appointment";
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

