package com.opsmind.api.serviceinstance.dto;

import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import com.opsmind.api.serviceinstance.entity.ServiceInstanceStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ServiceInstanceResponse(
        UUID id,
        UUID serviceId,
        String instanceName,
        String hostName,
        String environment,
        String region,
        ServiceInstanceStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

    public static ServiceInstanceResponse from(
            ServiceInstance instance
    ) {

        return new ServiceInstanceResponse(
                instance.getId(),
                instance.getService().getId(),
                instance.getInstanceName(),
                instance.getHostName(),
                instance.getEnvironment(),
                instance.getRegion(),
                instance.getStatus(),
                instance.getCreatedAt(),
                instance.getUpdatedAt()
        );
    }
}