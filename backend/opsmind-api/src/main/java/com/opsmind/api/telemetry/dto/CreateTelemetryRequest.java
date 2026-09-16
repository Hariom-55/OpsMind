package com.opsmind.api.telemetry.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTelemetryRequest(

        @NotNull(message = "Service instance ID is required")
        UUID serviceInstanceId,

        @NotNull(message = "Recorded timestamp is required")
        OffsetDateTime recordedAt,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "100.0")
        BigDecimal cpuUsage,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "100.0")
        BigDecimal memoryUsage,

        @PositiveOrZero(message = "Request count cannot be negative")
        Long requestCount,

        @PositiveOrZero(message = "Response time cannot be negative")
        BigDecimal responseTimeMs,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "100.0")
        BigDecimal errorRate,

        @PositiveOrZero(message = "Database connections cannot be negative")
        Integer databaseConnections,

        @PositiveOrZero(message = "Network latency cannot be negative")
        BigDecimal networkLatencyMs,

        @DecimalMin(value = "0.0")
        @DecimalMax(value = "100.0")
        BigDecimal availability
) {
}