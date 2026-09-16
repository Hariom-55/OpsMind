package com.opsmind.api.telemetry.entity;

import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "telemetry",
        indexes = {
                @Index(
                        name = "idx_telemetry_service_instance_recorded_at",
                        columnList = "service_instance_id, recorded_at"
                ),
                @Index(
                        name = "idx_telemetry_recorded_at",
                        columnList = "recorded_at"
                )
        }
)
public class Telemetry {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "service_instance_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_telemetry_service_instance"
            )
    )
    private ServiceInstance serviceInstance;

    @Column(name = "recorded_at", nullable = false)
    private OffsetDateTime recordedAt;

    @Column(name = "cpu_usage", precision = 5, scale = 2)
    private BigDecimal cpuUsage;

    @Column(name = "memory_usage", precision = 5, scale = 2)
    private BigDecimal memoryUsage;

    @Column(name = "request_count")
    private Long requestCount;

    @Column(name = "response_time_ms", precision = 12, scale = 2)
    private BigDecimal responseTimeMs;

    @Column(name = "error_rate", precision = 5, scale = 2)
    private BigDecimal errorRate;

    @Column(name = "database_connections")
    private Integer databaseConnections;

    @Column(name = "network_latency_ms", precision = 12, scale = 2)
    private BigDecimal networkLatencyMs;

    @Column(name = "availability", precision = 5, scale = 2)
    private BigDecimal availability;

    protected Telemetry() {
    }

    public Telemetry(
            ServiceInstance serviceInstance,
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
        this.id = UUID.randomUUID();
        this.serviceInstance = serviceInstance;
        this.recordedAt = recordedAt;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.requestCount = requestCount;
        this.responseTimeMs = responseTimeMs;
        this.errorRate = errorRate;
        this.databaseConnections = databaseConnections;
        this.networkLatencyMs = networkLatencyMs;
        this.availability = availability;
    }

    public UUID getId() {
        return id;
    }

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public OffsetDateTime getRecordedAt() {
        return recordedAt;
    }

    public BigDecimal getCpuUsage() {
        return cpuUsage;
    }

    public BigDecimal getMemoryUsage() {
        return memoryUsage;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public BigDecimal getResponseTimeMs() {
        return responseTimeMs;
    }

    public BigDecimal getErrorRate() {
        return errorRate;
    }

    public Integer getDatabaseConnections() {
        return databaseConnections;
    }

    public BigDecimal getNetworkLatencyMs() {
        return networkLatencyMs;
    }

    public BigDecimal getAvailability() {
        return availability;
    }
}