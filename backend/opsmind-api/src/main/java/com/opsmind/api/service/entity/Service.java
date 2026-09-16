package com.opsmind.api.service.entity;

import com.opsmind.api.application.entity.Application;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "services",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_service_application_name",
                        columnNames = {"application_id", "name"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_services_application_id",
                        columnList = "application_id"
                )
        }
)
public class Service {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "application_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_services_application")
    )
    private Application application;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "service_type", length = 100)
    private String serviceType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ServiceCriticality criticality = ServiceCriticality.MEDIUM;

    @Column(name = "sla_target", precision = 5, scale = 2)
    private BigDecimal slaTarget;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Service() {
    }

    public Service(
            Application application,
            String name,
            String serviceType,
            String description,
            ServiceCriticality criticality,
            BigDecimal slaTarget
    ) {
        this.id = UUID.randomUUID();
        this.application = application;
        this.name = name;
        this.serviceType = serviceType;
        this.description = description;
        this.criticality = criticality;
        this.slaTarget = slaTarget;
        this.active = true;
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public Application getApplication() {
        return application;
    }

    public String getName() {
        return name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getDescription() {
        return description;
    }

    public ServiceCriticality getCriticality() {
        return criticality;
    }

    public BigDecimal getSlaTarget() {
        return slaTarget;
    }

    public boolean isActive() {
        return active;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}