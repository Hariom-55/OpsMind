package com.opsmind.api.service.controller;

import com.opsmind.api.service.dto.CreateServiceRequest;
import com.opsmind.api.service.dto.ServiceResponse;
import com.opsmind.api.service.service.ServiceManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceManagementService serviceManagementService;

    public ServiceController(
            ServiceManagementService serviceManagementService
    ) {
        this.serviceManagementService = serviceManagementService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse createService(
            @Valid @RequestBody CreateServiceRequest request
    ) {
        return serviceManagementService.createService(request);
    }

    @GetMapping("/{id}")
    public ServiceResponse getService(
            @PathVariable UUID id
    ) {
        return serviceManagementService.getService(id);
    }

    @GetMapping("/application/{applicationId}")
    public List<ServiceResponse> getServicesByApplication(
            @PathVariable UUID applicationId
    ) {
        return serviceManagementService.getServicesByApplication(
                applicationId
        );
    }
}