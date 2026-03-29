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
public class StayService {

    private final RestTemplate restTemplate;
    @Value("${backend.base-url}")
    private String baseUrl;

    public StayService(RestTemplate restTemplate){

        this.restTemplate = restTemplate;
    }

    //get all stays
    public List<Map> getAllStays() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/stays/all", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //get stay by id
    public Map getStayById(int id) {
        try {
            return restTemplate.getForObject(baseUrl + "/stays/" + id, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    //get stay by patient
    public List<Map> getStayByPatient(int patientId) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/stays/patient/" + patientId,
                    Map[].class
            );
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //get stay by room
    public List<Map> getStayByRoom(int roomId) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/stays/room/" + roomId,
                    Map[].class
            );
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //create stay
    public Map createStay(Map stay) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(stay, headers);

            return restTemplate.postForObject(
                    baseUrl + "/stays/create",
                    request,
                    Map.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //update stay
    public void updateStay(int id, Map stay) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<>(stay, headers);

            restTemplate.exchange(
                    baseUrl + "/stays/" + id,
                    HttpMethod.PUT,
                    request,
                    Void.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
