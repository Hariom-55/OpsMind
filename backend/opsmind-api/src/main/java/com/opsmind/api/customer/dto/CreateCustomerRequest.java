
package com.opsmind.api.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(

        @NotBlank(message = "Customer name is required")
        @Size(max = 200, message = "Customer name cannot exceed 200 characters")
        String name,

        @Size(max = 100, message = "External reference cannot exceed 100 characters")
        String externalReference,

        @Email(message = "Contact email must be valid")
        @Size(max = 255, message = "Contact email cannot exceed 255 characters")
        String contactEmail
) {
}