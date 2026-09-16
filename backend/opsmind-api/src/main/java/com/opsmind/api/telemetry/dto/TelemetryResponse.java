package com.opsmind.api.telemetry.dto;

import com.opsmind.api.telemetry.entity.Telemetry;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TelemetryResponse(

        UUID id,

        UUID serviceInstanceId,

        OffsetDateTime recordedAt,

        BigDecimal cpuUsage,

        BigDecimal memoryUsage,

        Long requestCount,

        BigDecimal responseTimeMs,

        BigDecimal errorRate,

        Integer databaseConnections,

        BigDecimal networkLatencyMs,

        BigDecimal availability
) {

    public static TelemetryResponse from(Telemetry telemetry) {

        return new TelemetryResponse(
                telemetry.getId(),
                telemetry.getServiceInstance().getId(),
                telemetry.getRecordedAt(),
                telemetry.getCpuUsage(),
                telemetry.getMemoryUsage(),
                telemetry.getRequestCount(),
                telemetry.getResponseTimeMs(),
                telemetry.getErrorRate(),
                telemetry.getDatabaseConnections(),
                telemetry.getNetworkLatencyMs(),
                telemetry.getAvailability()
        );
    }
}