package com.opsmind.api.incident.controller;

import com.opsmind.api.incident.dto.IncidentResponse;
import com.opsmind.api.incident.service.IncidentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(
            IncidentService incidentService
    ) {
        this.incidentService = incidentService;
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncident(
            @PathVariable UUID id
    ) {
        return incidentService.getIncident(id);
    }

    @GetMapping("/service-instance/{serviceInstanceId}")
    public List<IncidentResponse> getIncidentsByServiceInstance(
            @PathVariable UUID serviceInstanceId
    ) {
        return incidentService.getIncidentsByServiceInstance(
                serviceInstanceId
        );
    }

    @GetMapping("/active")
    public List<IncidentResponse> getActiveIncidents() {
        return incidentService.getActiveIncidents();
    }
}