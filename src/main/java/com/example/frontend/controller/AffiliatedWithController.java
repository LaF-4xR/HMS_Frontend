package com.example.frontend.controller;

import com.example.frontend.service.AffiliatedWithService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("entity/ayan/AffiliatedWith")
public class AffiliatedWithController {

    private final AffiliatedWithService affiliatedWithService;

    public AffiliatedWithController(AffiliatedWithService affiliatedWithService){
        this.affiliatedWithService = affiliatedWithService;
    }

    @GetMapping
    public String affiliationPage(Model model){
        model.addAttribute("affiliations", affiliatedWithService.getAllAffiliations());
        return "affiliatedWith";
    }

    // Search by Composite ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam int physicianId, @RequestParam int departmentId, Model model) {
        model.addAttribute("affiliations", affiliatedWithService.getAllAffiliations());
        model.addAttribute("affiliationById", affiliatedWithService.getAffiliationById(physicianId, departmentId));
        return "affiliatedWith";
    }

    // Search by Physician
    @GetMapping("/searchByPhysician")
    public String searchByPhysician(@RequestParam int physicianId, Model model) {
        model.addAttribute("affiliations", affiliatedWithService.getAllAffiliations());
        model.addAttribute("affiliationsByPhysician", affiliatedWithService.getAffiliationsByPhysician(physicianId));
        return "affiliatedWith";
    }

    // Search by Department
    @GetMapping("/searchByDepartment")
    public String searchByDepartment(@RequestParam int departmentId, Model model) {
        model.addAttribute("affiliations", affiliatedWithService.getAllAffiliations());
        model.addAttribute("affiliationsByDepartment", affiliatedWithService.getAffiliationsByDepartment(departmentId));
        return "affiliatedWith";
    }

    // Create affiliation
    @PostMapping("/create")
    public String createAffiliation(@RequestParam Map<String, Object> affiliation, RedirectAttributes redirectAttributes){

        // Handle boolean conversion for primaryAffiliation
        if (affiliation.containsKey("primaryAffiliation")) {
            String val = affiliation.get("primaryAffiliation").toString();
            affiliation.put("primaryAffiliation", val.equalsIgnoreCase("true"));
        }

        Map result = affiliatedWithService.createAffiliation(affiliation);
        if (result == null) {
            redirectAttributes.addFlashAttribute("createError", "Create failed. Please check inputs.");
        } else {
            redirectAttributes.addFlashAttribute("createSuccess", "Affiliation created successfully.");
        }
        return "redirect:/entity/ayan/AffiliatedWith";
    }

    // Update form handler
    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int physician,
                             @RequestParam int department,
                             @RequestParam Map<String, Object> body,
                             RedirectAttributes redirectAttributes) {

        // Handle boolean conversion for primaryAffiliation
        if (body.containsKey("primaryAffiliation")) {
            String val = body.get("primaryAffiliation").toString();
            body.put("primaryAffiliation", val.equalsIgnoreCase("true"));
        }

        Map existing = affiliatedWithService.getAffiliationById(physician, department);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("updateError", "Update failed: Record not found.");
        } else {
            affiliatedWithService.updateAffiliation(physician, department, body);
            redirectAttributes.addFlashAttribute("updateSuccess", "Affiliation updated successfully.");
        }
        return "redirect:/entity/ayan/AffiliatedWith";
    }

    // Delete affiliation handler
    @PostMapping("/delete/{physicianId}/{departmentId}")
    public String deleteAffiliation(@PathVariable int physicianId, @PathVariable int departmentId, RedirectAttributes redirectAttributes) {
        Map existing = affiliatedWithService.getAffiliationById(physicianId, departmentId);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("deleteError", "Delete failed: Record not found.");
        } else {
            affiliatedWithService.deleteAffiliation(physicianId, departmentId);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Affiliation deleted successfully.");
        }
        return "redirect:/entity/ayan/AffiliatedWith";
    }
}
