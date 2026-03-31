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

    // Main Hub (The card grid)
    @GetMapping("")
    public String departmentPage(){
        return "department/department";
    }

    // 1. Get All Departments
    @GetMapping("/all")
    public String getAllDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department/all-departments";
    }

    // 2. Search by ID
    @GetMapping("/searchById")
    public String searchById(@RequestParam(required = false) Integer id, Model model) {
        if (id != null) {
            model.addAttribute("departmentById", departmentService.getDepartmentById(id));
        }
        return "department/department-id";
    }

    // 3. Search by Name
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            model.addAttribute("departmentByName", departmentService.getDepartmentByName(name));
        }
        return "department/department-name";
    }

    // 4. Search by Head
    @GetMapping("/searchByHead")
    public String searchByHead(@RequestParam(required = false) Integer head, Model model) {
        if (head != null) {
            model.addAttribute("departmentByHead", departmentService.getDepartmentByHead(head));
        }
        return "department/department-head";
    }

    // 5. Create Form & Action
    @GetMapping("/createForm")
    public String showCreateForm() {
        return "department/department-create";
    }

    @PostMapping("/create")
    public String createDepartment(@RequestParam Map<String, Object> department, RedirectAttributes redirectAttributes){
        try {
            // Assuming your service handles Map or you have adapted it
            Object result = departmentService.createDepartment(department);
            if (result == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Create failed. Please check inputs.");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Department created successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Create failed: " + e.getMessage());
        }
        return "redirect:/entity/ayan/Department/createForm";
    }

    // 6. Update Form & Action
    @GetMapping("/updateFormPage")
    public String showUpdateForm() {
        return "department/department-update";
    }

    @PostMapping("/updateForm")
    public String processUpdate(@RequestParam int id, @RequestParam Map<String, Object> body, RedirectAttributes redirectAttributes) {
        body.remove("id"); // Remove the search ID so it doesn't overwrite the payload
        try {
            Object existing = departmentService.getDepartmentById(id);
            if (existing == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Update failed: ID " + id + " not found.");
            } else {
                departmentService.updateDepartment(id, body);
                redirectAttributes.addFlashAttribute("successMessage", "Department ID " + id + " updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Update failed: " + e.getMessage());
        }
        return "redirect:/entity/ayan/Department/updateFormPage";
    }

    // Delete is kept functional behind the scenes if you need it via Postman
    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Department deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Delete failed: " + e.getMessage());
        }
        return "redirect:/entity/ayan/Department";
    }
}