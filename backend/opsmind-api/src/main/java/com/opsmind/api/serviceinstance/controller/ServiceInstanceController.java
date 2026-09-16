package com.opsmind.api.serviceinstance.controller;

import com.opsmind.api.serviceinstance.dto.CreateServiceInstanceRequest;
import com.opsmind.api.serviceinstance.dto.ServiceInstanceResponse;
import com.opsmind.api.serviceinstance.service.ServiceInstanceManagementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-instances")
public class ServiceInstanceController {

    private final ServiceInstanceManagementService serviceInstanceService;

    public ServiceInstanceController(
            ServiceInstanceManagementService serviceInstanceService
    ) {
        this.serviceInstanceService = serviceInstanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceInstanceResponse createInstance(
            @Valid @RequestBody CreateServiceInstanceRequest request
    ) {
        return serviceInstanceService.createInstance(request);
    }

    @GetMapping("/{id}")
    public ServiceInstanceResponse getInstance(
            @PathVariable UUID id
    ) {
        return serviceInstanceService.getInstance(id);
    }

    @GetMapping("/service/{serviceId}")
    public List<ServiceInstanceResponse> getInstancesByService(
            @PathVariable UUID serviceId
    ) {
        return serviceInstanceService.getInstancesByService(serviceId);
    }
}