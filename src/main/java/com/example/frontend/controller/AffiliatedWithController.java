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

    // Main Hub (The card grid)
    @GetMapping("")
    public String affiliationPage(){
        return "affiliatedWith/affiliatedWith";
    }

    // 1. Get All Affiliations
    @GetMapping("/all")
    public String getAllAffiliations(Model model) {
        model.addAttribute("affiliations", affiliatedWithService.getAllAffiliations());
        return "affiliatedWith/all-affiliations";
    }

    // 2. Search by Composite ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam(required = false) Integer physicianId,
                             @RequestParam(required = false) Integer departmentId,
                             Model model) {
        if (physicianId != null && departmentId != null) {
            model.addAttribute("affiliationById", affiliatedWithService.getAffiliationById(physicianId, departmentId));
        }
        return "affiliatedWith/affiliatedWith-id";
    }

    // 3. Search by Physician
    @GetMapping("/searchByPhysician")
    public String searchByPhysician(@RequestParam(required = false) Integer physicianId, Model model) {
        if (physicianId != null) {
            model.addAttribute("affiliationsByPhysician", affiliatedWithService.getAffiliationsByPhysician(physicianId));
        }
        return "affiliatedWith/affiliatedWith-physician";
    }

    // 4. Search by Department
    @GetMapping("/searchByDepartment")
    public String searchByDepartment(@RequestParam(required = false) Integer departmentId, Model model) {
        if (departmentId != null) {
            model.addAttribute("affiliationsByDepartment", affiliatedWithService.getAffiliationsByDepartment(departmentId));
        }
        return "affiliatedWith/affiliatedWith-department";
    }

    // 5. Create Form & Action
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "affiliatedWith/affiliatedWith-create";
    }

    @PostMapping("/create")
    public String createAffiliation(@RequestParam Map<String, Object> affiliation, RedirectAttributes redirectAttributes){
        try {
            // Handle boolean conversion for primaryAffiliation
            if (affiliation.containsKey("primaryAffiliation")) {
                String val = affiliation.get("primaryAffiliation").toString();
                affiliation.put("primaryAffiliation", val.equalsIgnoreCase("true"));
            }

            Object result = affiliatedWithService.createAffiliation(affiliation);
            if (result == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Create failed. Please check inputs.");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Affiliation created successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Create failed: " + e.getMessage());
        }
        return "redirect:/entity/ayan/AffiliatedWith/createForm";
    }

    // 6. Update Form & Action
    @GetMapping("/updateFormPage")
    public String showUpdateForm() {
        return "affiliatedWith/affiliatedWith-update";
    }

    @PostMapping("/updateForm")
    public String processUpdate(@RequestParam int physician,
                                @RequestParam int department,
                                @RequestParam Map<String, Object> body,
                                RedirectAttributes redirectAttributes) {
        try {
            // Handle boolean conversion for primaryAffiliation
            if (body.containsKey("primaryAffiliation")) {
                String val = body.get("primaryAffiliation").toString();
                body.put("primaryAffiliation", val.equalsIgnoreCase("true"));
            }

            Object existing = affiliatedWithService.getAffiliationById(physician, department);

            if (existing == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Update failed: Record not found.");
            } else {
                affiliatedWithService.updateAffiliation(physician, department, body);
                redirectAttributes.addFlashAttribute("successMessage", "Affiliation updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Update failed: " + e.getMessage());
        }
        return "redirect:/entity/ayan/AffiliatedWith/updateFormPage";
    }

    // Delete is kept functional behind the scenes
    @PostMapping("/delete/{physicianId}/{departmentId}")
    public String deleteAffiliation(@PathVariable int physicianId, @PathVariable int departmentId, RedirectAttributes redirectAttributes) {
        try {
            affiliatedWithService.deleteAffiliation(physicianId, departmentId);
            redirectAttributes.addFlashAttribute("successMessage", "Affiliation deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Delete failed: " + e.getMessage());
        }
        return "redirect:/entity/ayan/AffiliatedWith";
    }
}