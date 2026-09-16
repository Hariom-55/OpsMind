package com.opsmind.api.service.dto;

import com.opsmind.api.service.entity.ServiceCriticality;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateServiceRequest(

        @NotNull(message = "Application ID is required")
        UUID applicationId,

        @NotBlank(message = "Service name is required")
        @Size(max = 200, message = "Service name cannot exceed 200 characters")
        String name,

        @Size(max = 100, message = "Service type cannot exceed 100 characters")
        String serviceType,

        String description,

        ServiceCriticality criticality,

        @DecimalMin(value = "0.0", message = "SLA target cannot be negative")
        @DecimalMax(value = "100.0", message = "SLA target cannot exceed 100")
        BigDecimal slaTarget
) {
}