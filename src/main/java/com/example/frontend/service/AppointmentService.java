package com.example.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public AppointmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getAllAppointments() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/appointments", Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAppointmentById(int id) {
        try {
            return restTemplate.getForObject(baseUrl + "/appointments/" + id, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Map<String, Object>> getByPatientSsn(int patientSsn) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/appointments/patient/" + patientSsn, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getByNurseId(int nurseId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/appointments/nurse/" + nurseId, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getByPhysicianId(int physicianId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/appointments/physician/" + physicianId, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Map<String, Object> createAppointment(Map<String, Object> appointment) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(appointment, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/appointments",
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            return toTypedMap(response.getBody());
        } catch (HttpStatusCodeException e) {
            System.err.println("Error creating appointment: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            return null;
        }
    }

    public Map<String, Object> updateAppointment(int id, Map<String, Object> appointment) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(appointment, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/appointments/" + id,
                    HttpMethod.PUT,
                    request,
                    Map.class
            );

            return toTypedMap(response.getBody());
        } catch (HttpStatusCodeException e) {
            System.err.println("Error updating appointment: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("Error updating appointment: " + e.getMessage());
            return null;
        }
    }

    public void deleteAppointment(int id) {
        try {
            restTemplate.delete(baseUrl + "/appointments/" + id);
        } catch (Exception e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> toTypedList(Map[] result) {
        if (result == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(result)
                .map(item -> (Map<String, Object>) item)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> toTypedMap(Map body) {
        return body == null ? null : (Map<String, Object>) body;
    }
}

