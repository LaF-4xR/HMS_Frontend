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
import java.util.LinkedHashMap;

@Service
public class PrescribesService {

    private final RestTemplate restTemplate;

    @Value("${backend.base-url}")
    private String baseUrl;

    public PrescribesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getAllPrescribes() {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/prescribes", Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Map<String, Object> getById(int physicianId, int patientSsn, int medicationCode, String date) {
        try {
            Map response = restTemplate.getForObject(
                    baseUrl + "/prescribes/{physicianId}/{patientSsn}/{medicationCode}/{date}",
                    Map.class,
                    physicianId,
                    patientSsn,
                    medicationCode,
                    date
            );
            return toTypedMap(response);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Map<String, Object>> getByPhysician(int physicianId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/prescribes/physician/" + physicianId, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getByPatient(int patientSsn) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/prescribes/patient/" + patientSsn, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getByMedication(int medicationCode) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/prescribes/medication/" + medicationCode, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getByAppointment(int appointmentId) {
        try {
            Map[] result = restTemplate.getForObject(baseUrl + "/prescribes/appointment/" + appointmentId, Map[].class);
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> search(int physicianId, int patientSsn, int medicationCode) {
        try {
            Map<String, Object> uriVariables = new LinkedHashMap<>();
            uriVariables.put("physicianId", physicianId);
            uriVariables.put("patientSsn", patientSsn);
            uriVariables.put("medicationCode", medicationCode);

            Map[] result = restTemplate.getForObject(
                    baseUrl + "/prescribes/search?physicianId={physicianId}&patientSsn={patientSsn}&medicationCode={medicationCode}",
                    Map[].class,
                    uriVariables
            );
            return toTypedList(result);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Map<String, Object> createPrescribes(Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/prescribes",
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            return toTypedMap(response.getBody());
        } catch (HttpStatusCodeException e) {
            System.err.println("Error creating prescribes: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("Error creating prescribes: " + e.getMessage());
            return null;
        }
    }

    public Map<String, Object> updatePrescribes(int physicianId, int patientSsn, int medicationCode, String date, Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    baseUrl + "/prescribes/{physicianId}/{patientSsn}/{medicationCode}/{date}",
                    HttpMethod.PUT,
                    request,
                    Map.class,
                    physicianId,
                    patientSsn,
                    medicationCode,
                    date
            );

            return toTypedMap(response.getBody());
        } catch (HttpStatusCodeException e) {
            System.err.println("Error updating prescribes: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("Error updating prescribes: " + e.getMessage());
            return null;
        }
    }

    public void deletePrescribes(int physicianId, int patientSsn, int medicationCode, String date) {
        try {
            restTemplate.delete(
                    baseUrl + "/prescribes/{physicianId}/{patientSsn}/{medicationCode}/{date}",
                    physicianId,
                    patientSsn,
                    medicationCode,
                    date
            );
        } catch (Exception e) {
            System.err.println("Error deleting prescribes: " + e.getMessage());
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

