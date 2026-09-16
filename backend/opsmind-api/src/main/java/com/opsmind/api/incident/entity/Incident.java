package com.opsmind.api.incident.entity;

import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "incidents",
        indexes = {
                @Index(
                        name = "idx_incidents_service_instance",
                        columnList = "service_instance_id"
                ),
                @Index(
                        name = "idx_incidents_detected_at",
                        columnList = "detected_at"
                ),
                @Index(
                        name = "idx_incidents_status",
                        columnList = "status"
                )
        }
)
public class Incident {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "service_instance_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_incidents_service_instance"
            )
    )
    private ServiceInstance serviceInstance;

    @Enumerated(EnumType.STRING)
    @Column(name = "incident_type", nullable = false, length = 50)
    private IncidentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private IncidentSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private IncidentStatus status;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "metric_name", length = 100)
    private String metricName;

    @Column(name = "metric_value", precision = 12, scale = 2)
    private BigDecimal metricValue;

    @Column(name = "threshold_value", precision = 12, scale = 2)
    private BigDecimal thresholdValue;

    @Column(name = "detected_at", nullable = false)
    private OffsetDateTime detectedAt;

    @Column(name = "resolved_at")
    private OffsetDateTime resolvedAt;

    protected Incident() {
    }

    public Incident(
            ServiceInstance serviceInstance,
            IncidentType type,
            IncidentSeverity severity,
            String title,
            String description,
            String metricName,
            BigDecimal metricValue,
            BigDecimal thresholdValue
    ) {
        this.id = UUID.randomUUID();
        this.serviceInstance = serviceInstance;
        this.type = type;
        this.severity = severity;
        this.status = IncidentStatus.DETECTED;
        this.title = title;
        this.description = description;
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.thresholdValue = thresholdValue;
        this.detectedAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public ServiceInstance getServiceInstance() {
        return serviceInstance;
    }

    public IncidentType getType() {
        return type;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMetricName() {
        return metricName;
    }

    public BigDecimal getMetricValue() {
        return metricValue;
    }

    public BigDecimal getThresholdValue() {
        return thresholdValue;
    }

    public OffsetDateTime getDetectedAt() {
        return detectedAt;
    }

    public OffsetDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setStatus(IncidentStatus status) {
        this.status = status;

        if (status == IncidentStatus.RESOLVED) {
            this.resolvedAt = OffsetDateTime.now();
        }
    }
}