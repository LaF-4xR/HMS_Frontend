package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.*;

@Service
public class UndergoesService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public UndergoesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // GET ALL
    public List<Map> getAll() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/undergoes", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY COMPOSITE ID
    public Map getById(int patient, int procedure, int stay, String date) {
        try {
            String url = baseUrl + "/undergoes/" + patient + "/" + procedure + "/" + stay + "/" + date;
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    // GET BY PATIENT
    public List<Map> getByPatient(int patientId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/undergoes/patient/" + patientId, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY PROCEDURE
    public List<Map> getByProcedure(int procedureId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/undergoes/procedure/" + procedureId, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY STAY
    public List<Map> getByStay(int stayId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/undergoes/stay/" + stayId, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // GET BY DATE RANGE
    public List<Map> getByDateRange(String start, String end) {
        try {
            String url = baseUrl + "/undergoes/date-range?start=" + start + "&end=" + end;
            Map[] result = restTemplate.getForObject(url, Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // CREATE
    public Map create(Map undergoes) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(undergoes, headers);

            return restTemplate.postForObject(baseUrl + "/undergoes", request, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // UPDATE
    public void update(int patient, int procedure, int stay, String date, Map undergoes) {
        try {
            String url = baseUrl + "/undergoes/" + patient + "/" + procedure + "/" + stay + "/" + date;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(undergoes, headers);

            restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(int patient, int procedure, int stay, String date) {
        try {
            String url = baseUrl + "/undergoes/" + patient + "/" + procedure + "/" + stay + "/" + date;
            restTemplate.delete(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}