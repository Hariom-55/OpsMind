package com.opsmind.api.application.controller;

import com.opsmind.api.application.dto.ApplicationResponse;
import com.opsmind.api.application.dto.CreateApplicationRequest;
import com.opsmind.api.application.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService
    ) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse createApplication(
            @Valid @RequestBody CreateApplicationRequest request
    ) {
        return applicationService.createApplication(request);
    }

    @GetMapping("/{id}")
    public ApplicationResponse getApplication(
            @PathVariable UUID id
    ) {
        return applicationService.getApplication(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<ApplicationResponse> getApplicationsByCustomer(
            @PathVariable UUID customerId
    ) {
        return applicationService.getApplicationsByCustomer(customerId);
    }
}