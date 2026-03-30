package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class ProcedureService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public ProcedureService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // GET ALL
    public List<Map> getAll() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/procedures", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY ID
    public Map getById(int code) {
        try {
            return restTemplate.getForObject(baseUrl + "/procedures/" + code, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    // SEARCH BY NAME
    public List<Map> searchByName(String name) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/procedures/search?name=" + name, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // FILTER BY COST
    public List<Map> filterByCost(double min, double max) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/procedures/filterByCost?min=" + min + "&max=" + max, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // CREATE
    public Map create(Map procedure) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map> request = new HttpEntity<>(procedure, headers);
            return restTemplate.postForObject(baseUrl + "/procedures", request, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE
    public void update(int code, Map procedure) {
        try {
            String url = baseUrl + "/procedures/" + code;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map> request = new HttpEntity<>(procedure, headers);
            restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int code) {
        try {
            restTemplate.delete(baseUrl + "/procedures/" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}