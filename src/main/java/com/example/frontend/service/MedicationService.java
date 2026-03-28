package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MedicationService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public MedicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //get all medication
    public List<Map> getAllMedication() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl+"/medications", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //get medication by code
    public Map getMedicationByCode(int code) {
        try {
            return restTemplate.getForObject(baseUrl + "/medications/" + code, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    //get medication by name
    public List<Map> getMedicationByName(String name) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/medications/name/" + name, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //get medcation by brand
    public List<Map> getMedicationByBrand(String brand) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/medications/brand/" + brand, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //create medication
    public Map createMedication(Map medication) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(medication, headers);

            return restTemplate.postForObject(
                    baseUrl + "/medications",
                    request,
                    Map.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //update medication
    public void updateMedication(int id, Map medication) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(medication, headers);

            restTemplate.exchange(
                    baseUrl + "/medications/" + id,
                    HttpMethod.PUT,
                    request,
                    Void.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
