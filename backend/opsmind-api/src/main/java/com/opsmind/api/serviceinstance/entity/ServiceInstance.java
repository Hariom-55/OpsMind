package com.opsmind.api.serviceinstance.entity;

import com.opsmind.api.service.entity.Service;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "service_instances",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_service_instance_name",
                        columnNames = {"service_id", "instance_name"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_service_instances_service_id",
                        columnList = "service_id"
                )
        }
)
public class ServiceInstance {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "service_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_service_instances_service")
    )
    private Service service;

    @Column(name = "instance_name", nullable = false, length = 200)
    private String instanceName;

    @Column(name = "host_name", length = 255)
    private String hostName;

    @Column(nullable = false, length = 50)
    private String environment;

    @Column(length = 100)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ServiceInstanceStatus status = ServiceInstanceStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected ServiceInstance() {
    }

    public ServiceInstance(
            Service service,
            String instanceName,
            String hostName,
            String environment,
            String region
    ) {
        this.id = UUID.randomUUID();
        this.service = service;
        this.instanceName = instanceName;
        this.hostName = hostName;
        this.environment = environment;
        this.region = region;
        this.status = ServiceInstanceStatus.ACTIVE;
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

    public Service getService() {
        return service;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getRegion() {
        return region;
    }

    public ServiceInstanceStatus getStatus() {
        return status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}