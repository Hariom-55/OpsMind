package com.opsmind.api.service.dto;

import com.opsmind.api.service.entity.Service;
import com.opsmind.api.service.entity.ServiceCriticality;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ServiceResponse(
        UUID id,
        UUID applicationId,
        String name,
        String serviceType,
        String description,
        ServiceCriticality criticality,
        BigDecimal slaTarget,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

    public static ServiceResponse from(Service service) {

        return new ServiceResponse(
                service.getId(),
                service.getApplication().getId(),
                service.getName(),
                service.getServiceType(),
                service.getDescription(),
                service.getCriticality(),
                service.getSlaTarget(),
                service.isActive(),
                service.getCreatedAt(),
                service.getUpdatedAt()
        );
    }
}