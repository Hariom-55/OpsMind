package com.opsmind.api.incident.dto;

import com.opsmind.api.incident.entity.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record IncidentResponse(

        UUID id,

        UUID serviceInstanceId,

        IncidentType type,

        IncidentSeverity severity,

        IncidentStatus status,

        String title,

        String description,

        String metricName,

        BigDecimal metricValue,

        BigDecimal thresholdValue,

        OffsetDateTime detectedAt,

        OffsetDateTime resolvedAt
) {

    public static IncidentResponse from(Incident incident) {

        return new IncidentResponse(
                incident.getId(),
                incident.getServiceInstance().getId(),
                incident.getType(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getMetricName(),
                incident.getMetricValue(),
                incident.getThresholdValue(),
                incident.getDetectedAt(),
                incident.getResolvedAt()
        );
    }
}