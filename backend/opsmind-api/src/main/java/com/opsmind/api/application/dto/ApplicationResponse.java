package com.opsmind.api.application.dto;

import com.opsmind.api.application.entity.Application;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicationResponse(
        UUID id,
        UUID customerId,
        String name,
        String description,
        String environment,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

    public static ApplicationResponse from(Application application) {

        return new ApplicationResponse(
                application.getId(),
                application.getCustomer().getId(),
                application.getName(),
                application.getDescription(),
                application.getEnvironment(),
                application.isActive(),
                application.getCreatedAt(),
                application.getUpdatedAt()
        );
    }
}