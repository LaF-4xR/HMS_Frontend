package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class TrainedInService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public TrainedInService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // GET ALL
    public List<Map> getAll() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/trainedin", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY COMPOSITE ID
    public Map getById(int physician, int treatment) {
        try {
            String url = baseUrl + "/trainedin/" + physician + "/" + treatment;
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    // GET BY PHYSICIAN
    public List<Map> getByPhysician(int physicianId) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/trainedin/physician/" + physicianId, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY TREATMENT
    public List<Map> getByTreatment(int treatmentId) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/trainedin/procedure/" + treatmentId, Map[].class);  // ← fixed: procedure not treatment
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // CREATE
    public Map create(Map trainedIn) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(trainedIn, headers);

            return restTemplate.postForObject(baseUrl + "/trainedin", request, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE
    public void update(int physician, int treatment, Map trainedIn) {
        try {
            String url = baseUrl + "/trainedin/" + physician + "/" + treatment;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(trainedIn, headers);

            restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int physician, int treatment) {
        try {
            String url = baseUrl + "/trainedin/" + physician + "/" + treatment;
            restTemplate.delete(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}