package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public PatientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map> getAllPatients() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/patients", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Map getPatientBySsn(int ssn) {
        try {
            return restTemplate.getForObject(baseUrl + "/patients/" + ssn, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Map> getPatientByName(String name) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/patients/name/" + name, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map> getPatientByPcp(Integer pcp) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/patients/pcp/" + pcp, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Map createPatient(Map<String, Object> patient) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(patient, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/patients",
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            System.err.println("Error creating patient: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("Error creating patient: " + e.getMessage());
            return null;
        }
    }

    public void updatePatient(int ssn, Map<String, Object> patient) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(patient, headers);
            restTemplate.exchange(
                    baseUrl + "/patients/" + ssn,
                    HttpMethod.PUT,
                    request,
                    Void.class
            );
        } catch (HttpStatusCodeException e) {
            System.err.println("Error updating patient: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("Error updating patient: " + e.getMessage());
        }
    }

    public void deletePatient(int ssn) {
        try {
            restTemplate.delete(baseUrl + "/patients/" + ssn);
        } catch (Exception e) {
            System.err.println("Error deleting patient");
        }
    }
}

