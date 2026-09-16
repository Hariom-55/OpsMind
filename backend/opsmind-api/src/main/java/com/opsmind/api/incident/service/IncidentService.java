package com.opsmind.api.incident.service;

import com.opsmind.api.incident.dto.IncidentResponse;
import com.opsmind.api.incident.entity.Incident;
import com.opsmind.api.incident.entity.IncidentStatus;
import com.opsmind.api.incident.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(
            IncidentRepository incidentRepository
    ) {
        this.incidentRepository = incidentRepository;
    }

    @Transactional(readOnly = true)
    public IncidentResponse getIncident(UUID id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Incident not found: " + id
                        )
                );

        return IncidentResponse.from(incident);
    }

    @Transactional(readOnly = true)
    public List<IncidentResponse> getIncidentsByServiceInstance(
            UUID serviceInstanceId
    ) {

        return incidentRepository
                .findByServiceInstanceIdOrderByDetectedAtDesc(
                        serviceInstanceId
                )
                .stream()
                .map(IncidentResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<IncidentResponse> getActiveIncidents() {

        return incidentRepository
                .findByStatusOrderByDetectedAtDesc(
                        IncidentStatus.DETECTED
                )
                .stream()
                .map(IncidentResponse::from)
                .toList();
    }
}