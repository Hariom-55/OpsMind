package com.opsmind.api.serviceinstance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateServiceInstanceRequest(

        @NotNull(message = "Service ID is required")
        UUID serviceId,

        @NotBlank(message = "Instance name is required")
        @Size(max = 200, message = "Instance name cannot exceed 200 characters")
        String instanceName,

        @Size(max = 255, message = "Host name cannot exceed 255 characters")
        String hostName,

        @NotBlank(message = "Environment is required")
        @Size(max = 50, message = "Environment cannot exceed 50 characters")
        String environment,

        @Size(max = 100, message = "Region cannot exceed 100 characters")
        String region
) {
}