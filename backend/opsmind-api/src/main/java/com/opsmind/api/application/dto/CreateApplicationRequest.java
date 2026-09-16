package com.opsmind.api.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateApplicationRequest(

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotBlank(message = "Application name is required")
        @Size(max = 200, message = "Application name cannot exceed 200 characters")
        String name,

        String description,

        @NotBlank(message = "Environment is required")
        @Size(max = 50, message = "Environment cannot exceed 50 characters")
        String environment
) {
}