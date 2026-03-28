package com.example.frontend.controller;

import com.example.frontend.service.StayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("entity/soumadwip/Stay")
public class StayController {

    private StayService stayService;

    public StayController(StayService stayService){
        this.stayService = stayService;
    }

    //get all stay
    @GetMapping
    public String stayPage(Model model){
        model.addAttribute("stays", stayService.getAllStays());
        return "stay";
    }

    //get by id
    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("stays", stayService.getAllStays());
        model.addAttribute("stayById", stayService.getStayById(id));
        return "stay";
    }

    //get by patient
    @GetMapping("/searchByPatient")
    public String searchByPatient(@RequestParam int patientId, Model model) {
        model.addAttribute("stays", stayService.getAllStays());
        model.addAttribute("stayByPatient", stayService.getStayByPatient(patientId));
        return "stay";
    }

    //get by room
    @GetMapping("/searchByRoom")
    public String searchByRoom(@RequestParam int roomId, Model model) {
        model.addAttribute("stays", stayService.getAllStays());
        model.addAttribute("stayByRoom", stayService.getStayByRoom(roomId));
        return "stay";
    }

    //create
    @PostMapping("/create")
    public String createStay(@RequestParam Map<String, Object> stay){

        // Optional: convert values if needed (dates, etc.)
        stayService.createStay(stay);

        return "redirect:/entity/soumadwip/Stay";
    }

    //update
    @PostMapping("/update/{id}")
    public String updateStay(@PathVariable int id,
                             @RequestParam Map<String, Object> stay){

        stayService.updateStay(id, stay);

        return "redirect:/entity/soumadwip/Stay";
    }

    //update form
    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int id,
                             @RequestParam Map<String, Object> body) {

        body.remove("id");
        stayService.updateStay(id, body);

        return "redirect:/entity/soumadwip/Stay";
    }


}
