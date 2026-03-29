package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RoomService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public RoomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map> getAllRooms() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/room/all", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // ✅ CREATE ROOM
    public void createRoom(Map<String, Object> room) {
        restTemplate.postForObject(
                baseUrl + "/room/create",
                room,
                Map.class
        );
    }

    // ✅ UPDATE ROOM
    public void updateRoom(int id, Map<String, Object> room) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(room, headers);

        restTemplate.exchange(
                baseUrl + "/room/update/" + id,
                HttpMethod.PUT,
                request,
                Void.class
        );
    }

    public List<Map> getRoomsByType(String type) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/room/type/" + type,
                    Map[].class
            );
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Map getRoomById(int id) {
        try {
            return restTemplate.getForObject(baseUrl + "/room/" + id, Map.class);
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }
}