package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;

@Service
public class OnCallService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public OnCallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // GET ALL
    public List<Map> getAllOnCall() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/oncall", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY NURSE ID
    public List<Map> getByNurse(int id) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/oncall/nurse/" + id, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map> getBlocksByFloor(int blockFloor) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/oncall/floor/" + blockFloor,
                    Map[].class
            );
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map> getBlocksByCode(int blockCode) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/oncall/code/" + blockCode,
                    Map[].class
            );
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // CREATE
    public Map createOnCall(Map<String, Object> body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/oncall",
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            System.err.println("Error creating onCall: " + e.getResponseBodyAsString());
            return null;
        }
    }
}