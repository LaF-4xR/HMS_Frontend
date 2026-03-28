package com.example.frontend.controller;

import com.example.frontend.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("entity/ayan/Department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    @GetMapping
    public String departmentPage(Model model){
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department";
    }

    // Search by ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam int id, Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("departmentById", departmentService.getDepartmentById(id));
        return "department";
    }

    // Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam String name, Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("departmentByName", departmentService.getDepartmentByName(name));
        return "department";
    }

    // Search by Head
    @GetMapping("/searchByHead")
    public String searchByHead(@RequestParam int head, Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("departmentByHead", departmentService.getDepartmentByHead(head));
        return "department";
    }

    // Create department
    @PostMapping("/create")
    public String createDepartment(@RequestParam Map<String, Object> department, RedirectAttributes redirectAttributes){
        Map result = departmentService.createDepartment(department);
        if (result == null) {
            redirectAttributes.addFlashAttribute("createError", "Create failed. Please check inputs.");
        } else {
            redirectAttributes.addFlashAttribute("createSuccess", "Department created successfully.");
        }
        return "redirect:/entity/ayan/Department";
    }

    // Update form handler
    @PostMapping("/updateForm")
    public String updateForm(@RequestParam int id,
                             @RequestParam Map<String, Object> body,
                             RedirectAttributes redirectAttributes) {
        body.remove("id"); // Remove the search ID so it doesn't overwrite the payload
        Map existing = departmentService.getDepartmentById(id);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("updateError", "Update failed: ID " + id + " not found.");
        } else {
            departmentService.updateDepartment(id, body);
            redirectAttributes.addFlashAttribute("updateSuccess", "Department updated successfully.");
        }
        return "redirect:/entity/ayan/Department";
    }

    // Delete department handler
    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Map existing = departmentService.getDepartmentById(id);

        if (existing == null) {
            redirectAttributes.addFlashAttribute("deleteError", "Delete failed: ID " + id + " not found.");
        } else {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Department deleted successfully.");
        }
        return "redirect:/entity/ayan/Department";
    }
}