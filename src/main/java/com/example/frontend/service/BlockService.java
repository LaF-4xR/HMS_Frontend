package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BlockService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public BlockService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map> getAllBlocks() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/blocks/allblock", Map[].class);
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void createBlock(Map<String, Object> block) {
        restTemplate.postForObject(baseUrl + "/blocks/createblock", block, Map.class);
    }

    public void updateBlock(int floor, int code, Map<String, Object> block) {
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(block);
        restTemplate.exchange(
                baseUrl + "/blocks/updateblock/" + floor + "/" + code,
                HttpMethod.PUT,
                request,
                Void.class
        );
    }

    public void deleteBlock(int floor, int code) {
        restTemplate.delete(baseUrl + "/blocks/deleteblock/" + floor + "/" + code);
    }
    public List<Map> getBlocksByFloor(int blockFloor) {
        try {
            Map[] result = restTemplate.getForObject(
                    baseUrl + "/blocks/floor/" + blockFloor,
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
                    baseUrl + "/blocks/code/" + blockCode,
                    Map[].class
            );
            return result != null ? Arrays.asList(result) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}