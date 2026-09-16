package com.opsmind.api.incident.service;

import com.opsmind.api.incident.entity.*;
import com.opsmind.api.incident.repository.IncidentRepository;
import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import com.opsmind.api.telemetry.entity.Telemetry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class IncidentDetectionService {

    private static final BigDecimal CPU_THRESHOLD =
            BigDecimal.valueOf(90);

    private static final BigDecimal MEMORY_THRESHOLD =
            BigDecimal.valueOf(90);

    private static final BigDecimal RESPONSE_TIME_THRESHOLD =
            BigDecimal.valueOf(1000);

    private static final BigDecimal ERROR_RATE_THRESHOLD =
            BigDecimal.valueOf(5);

    private final IncidentRepository incidentRepository;

    public IncidentDetectionService(
            IncidentRepository incidentRepository
    ) {
        this.incidentRepository = incidentRepository;
    }

    public void detectIncidents(Telemetry telemetry) {

        ServiceInstance instance =
                telemetry.getServiceInstance();

        checkCpu(telemetry, instance);

        checkMemory(telemetry, instance);

        checkResponseTime(telemetry, instance);

        checkErrorRate(telemetry, instance);

        checkSla(telemetry, instance);
    }

    private void checkCpu(
            Telemetry telemetry,
            ServiceInstance instance
    ) {

        if (telemetry.getCpuUsage() == null) {
            return;
        }

        if (telemetry.getCpuUsage().compareTo(CPU_THRESHOLD) <= 0) {
            return;
        }

        createIncidentIfNotActive(
                instance,
                IncidentType.HIGH_CPU,
                IncidentSeverity.HIGH,
                "High CPU utilization detected",
                "CPU utilization has exceeded the configured threshold.",
                "cpu_usage",
                telemetry.getCpuUsage(),
                CPU_THRESHOLD
        );
    }

    private void checkMemory(
            Telemetry telemetry,
            ServiceInstance instance
    ) {

        if (telemetry.getMemoryUsage() == null) {
            return;
        }

        if (telemetry.getMemoryUsage().compareTo(MEMORY_THRESHOLD) <= 0) {
            return;
        }

        createIncidentIfNotActive(
                instance,
                IncidentType.HIGH_MEMORY,
                IncidentSeverity.HIGH,
                "High memory utilization detected",
                "Memory utilization has exceeded the configured threshold.",
                "memory_usage",
                telemetry.getMemoryUsage(),
                MEMORY_THRESHOLD
        );
    }

    private void checkResponseTime(
            Telemetry telemetry,
            ServiceInstance instance
    ) {

        if (telemetry.getResponseTimeMs() == null) {
            return;
        }

        if (telemetry.getResponseTimeMs()
                .compareTo(RESPONSE_TIME_THRESHOLD) <= 0) {
            return;
        }

        createIncidentIfNotActive(
                instance,
                IncidentType.HIGH_LATENCY,
                IncidentSeverity.HIGH,
                "High response latency detected",
                "Service response time has exceeded the configured threshold.",
                "response_time_ms",
                telemetry.getResponseTimeMs(),
                RESPONSE_TIME_THRESHOLD
        );
    }

    private void checkErrorRate(
            Telemetry telemetry,
            ServiceInstance instance
    ) {

        if (telemetry.getErrorRate() == null) {
            return;
        }

        if (telemetry.getErrorRate()
                .compareTo(ERROR_RATE_THRESHOLD) <= 0) {
            return;
        }

        createIncidentIfNotActive(
                instance,
                IncidentType.HIGH_ERROR_RATE,
                IncidentSeverity.HIGH,
                "High error rate detected",
                "Service error rate has exceeded the configured threshold.",
                "error_rate",
                telemetry.getErrorRate(),
                ERROR_RATE_THRESHOLD
        );
    }

    private void checkSla(
            Telemetry telemetry,
            ServiceInstance instance
    ) {

        if (telemetry.getAvailability() == null) {
            return;
        }

        if (instance.getService().getSlaTarget() == null) {
            return;
        }

        BigDecimal slaTarget =
                instance.getService().getSlaTarget();

        if (telemetry.getAvailability()
                .compareTo(slaTarget) >= 0) {
            return;
        }

        createIncidentIfNotActive(
                instance,
                IncidentType.SLA_BREACH,
                IncidentSeverity.CRITICAL,
                "SLA availability breach detected",
                "Service availability has fallen below the configured SLA target.",
                "availability",
                telemetry.getAvailability(),
                slaTarget
        );
    }

    private void createIncidentIfNotActive(
            ServiceInstance instance,
            IncidentType type,
            IncidentSeverity severity,
            String title,
            String description,
            String metricName,
            BigDecimal metricValue,
            BigDecimal threshold
    ) {

        List<IncidentStatus> activeStatuses = List.of(
                IncidentStatus.DETECTED,
                IncidentStatus.ANALYZING,
                IncidentStatus.ACTION_REQUIRED,
                IncidentStatus.ENGINEER_REVIEW,
                IncidentStatus.ACTION_APPROVED
        );

        boolean alreadyActive =
                incidentRepository
                        .existsByServiceInstanceIdAndTypeAndStatusIn(
                                instance.getId(),
                                type,
                                activeStatuses
                        );

        if (alreadyActive) {
            return;
        }

        Incident incident = new Incident(
                instance,
                type,
                severity,
                title,
                description,
                metricName,
                metricValue,
                threshold
        );

        incidentRepository.save(incident);
    }
}