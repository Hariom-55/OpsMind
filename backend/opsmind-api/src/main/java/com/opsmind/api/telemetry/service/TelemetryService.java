package com.opsmind.api.telemetry.service;

import com.opsmind.api.incident.service.IncidentDetectionService;
import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import com.opsmind.api.serviceinstance.repository.ServiceInstanceRepository;
import com.opsmind.api.telemetry.dto.CreateTelemetryRequest;
import com.opsmind.api.telemetry.dto.TelemetryResponse;
import com.opsmind.api.telemetry.entity.Telemetry;
import com.opsmind.api.telemetry.repository.TelemetryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final ServiceInstanceRepository serviceInstanceRepository;
    private final IncidentDetectionService incidentDetectionService;

    public TelemetryService(
            TelemetryRepository telemetryRepository,
            ServiceInstanceRepository serviceInstanceRepository,
            IncidentDetectionService incidentDetectionService
    ) {
        this.telemetryRepository = telemetryRepository;
        this.serviceInstanceRepository = serviceInstanceRepository;
        this.incidentDetectionService = incidentDetectionService;
    }

    public TelemetryResponse recordTelemetry(
            CreateTelemetryRequest request
    ) {

        ServiceInstance serviceInstance =
                serviceInstanceRepository.findById(
                        request.serviceInstanceId()
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "Service instance not found: "
                                        + request.serviceInstanceId()
                        )
                );

        Telemetry telemetry = new Telemetry(
                serviceInstance,
                request.recordedAt(),
                request.cpuUsage(),
                request.memoryUsage(),
                request.requestCount(),
                request.responseTimeMs(),
                request.errorRate(),
                request.databaseConnections(),
                request.networkLatencyMs(),
                request.availability()
        );

        Telemetry savedTelemetry = telemetryRepository.save(telemetry);

        incidentDetectionService.detectIncidents(
                savedTelemetry
        );

        return TelemetryResponse.from(savedTelemetry);
    }

    @Transactional(readOnly = true)
    public List<TelemetryResponse> getLatestTelemetry(
            UUID serviceInstanceId
    ) {

        if (!serviceInstanceRepository.existsById(serviceInstanceId)) {
            throw new IllegalArgumentException(
                    "Service instance not found: " + serviceInstanceId
            );
        }

        return telemetryRepository
                .findByServiceInstanceIdOrderByRecordedAtDesc(
                        serviceInstanceId
                )
                .stream()
                .map(TelemetryResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TelemetryResponse> getTelemetryBetween(
            UUID serviceInstanceId,
            OffsetDateTime start,
            OffsetDateTime end
    ) {

        if (!serviceInstanceRepository.existsById(serviceInstanceId)) {
            throw new IllegalArgumentException(
                    "Service instance not found: " + serviceInstanceId
            );
        }

        return telemetryRepository
                .findByServiceInstanceIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        serviceInstanceId,
                        start,
                        end
                )
                .stream()
                .map(TelemetryResponse::from)
                .toList();
    }
}