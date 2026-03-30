package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class AffiliatedWithService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public AffiliatedWithService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    // Get all affiliations
    public List<Map> getAllAffiliations() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/affiliations", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Get affiliation by composite ID
    public Map getAffiliationById(int physicianId, int departmentId) {
        try {
            return restTemplate.getForObject(baseUrl + "/affiliations/" + physicianId + "/" + departmentId, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    // Get affiliations by physician
    public List<Map> getAffiliationsByPhysician(int physicianId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/affiliations/physician/" + physicianId, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Get affiliations by department
    public List<Map> getAffiliationsByDepartment(int departmentId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/affiliations/department/" + departmentId, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Create affiliation
    public Map createAffiliation(Map affiliation) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(affiliation, headers);

            return restTemplate.postForObject(
                    baseUrl + "/affiliations",
                    request,
                    Map.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update affiliation
    public void updateAffiliation(int physicianId, int departmentId, Map affiliation) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(affiliation, headers);

            restTemplate.exchange(
                    baseUrl + "/affiliations/" + physicianId + "/" + departmentId,
                    HttpMethod.PUT,
                    request,
                    Void.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete affiliation
    public void deleteAffiliation(int physicianId, int departmentId) {
        try {
            restTemplate.delete(baseUrl + "/affiliations/" + physicianId + "/" + departmentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}