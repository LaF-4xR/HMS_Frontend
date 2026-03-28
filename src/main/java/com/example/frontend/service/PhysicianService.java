package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class PhysicianService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public PhysicianService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    // Get all physicians
    public List<Map> getAllPhysicians() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/physicians", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Get physician by id
    public Map getPhysicianById(int id) {
        try {
            return restTemplate.getForObject(baseUrl + "/physicians/" + id, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    // Get physician by name
    public List<Map> getPhysicianByName(String name) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/physicians/name/" + name, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Get physician by position
    public List<Map> getPhysicianByPosition(String position) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/physicians/position/" + position, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Create physician
    public Map createPhysician(Map physician) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(physician, headers);

            return restTemplate.postForObject(
                    baseUrl + "/physicians",
                    request,
                    Map.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update physician
    public void updatePhysician(int id, Map physician) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(physician, headers);

            restTemplate.exchange(
                    baseUrl + "/physicians/" + id,
                    HttpMethod.PUT,
                    request,
                    Void.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete physician
    public void deletePhysician(int id) {
        try {
            restTemplate.delete(baseUrl + "/physicians/" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}