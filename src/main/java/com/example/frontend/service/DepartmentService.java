package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class DepartmentService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public DepartmentService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    // Get all departments
    public List<Map> getAllDepartments() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/departments", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Get department by id
    public Map getDepartmentById(int id) {
        try {
            return restTemplate.getForObject(baseUrl + "/departments/" + id, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    // Get department by name
    public List<Map> getDepartmentByName(String name) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/departments/name/" + name, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Get department by head
    public List<Map> getDepartmentByHead(int head) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/departments/head/" + head, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Create department
    public Map createDepartment(Map department) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(department, headers);

            return restTemplate.postForObject(
                    baseUrl + "/departments",
                    request,
                    Map.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update department
    public void updateDepartment(int id, Map department) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(department, headers);

            restTemplate.exchange(
                    baseUrl + "/departments/" + id,
                    HttpMethod.PUT,
                    request,
                    Void.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete department
    public void deleteDepartment(int id) {
        try {
            restTemplate.delete(baseUrl + "/departments/" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
