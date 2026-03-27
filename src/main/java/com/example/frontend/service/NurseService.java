package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class NurseService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public NurseService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //get all nurses
    public List<Map> getAllNurses() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl+"/nurses", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //get nurse by id
    public Map getNurseById(int id) {
        try {
            return restTemplate.getForObject(baseUrl + "/nurses/" + id, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    //get nurse by name
    public List<Map> getNurseByName(String name) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/nurses/name/" + name, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //get nurse by position
    public List<Map> getNurseByPosition(String position) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/nurses/position/" + position, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //create nurse
    public Map createNurse(Map nurse) {
        try {
            return restTemplate.postForObject(baseUrl + "/nurses", nurse, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    //update nurse
    public void updateNurse(int id, Map nurse) {
        try {
            restTemplate.put(baseUrl + "/nurses/" + id, nurse);
        } catch (Exception e) {
            System.err.println("Error updating nurse: ");
        }
    }
}
