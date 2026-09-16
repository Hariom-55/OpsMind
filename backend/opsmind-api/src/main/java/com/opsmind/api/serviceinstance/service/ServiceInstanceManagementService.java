package com.opsmind.api.serviceinstance.service;

import com.opsmind.api.service.entity.Service;
import com.opsmind.api.service.repository.ServiceRepository;
import com.opsmind.api.serviceinstance.dto.CreateServiceInstanceRequest;
import com.opsmind.api.serviceinstance.dto.ServiceInstanceResponse;
import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import com.opsmind.api.serviceinstance.repository.ServiceInstanceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class ServiceInstanceManagementService {

    private final ServiceInstanceRepository serviceInstanceRepository;
    private final ServiceRepository serviceRepository;

    public ServiceInstanceManagementService(
            ServiceInstanceRepository serviceInstanceRepository,
            ServiceRepository serviceRepository
    ) {
        this.serviceInstanceRepository = serviceInstanceRepository;
        this.serviceRepository = serviceRepository;
    }

    public ServiceInstanceResponse createInstance(
            CreateServiceInstanceRequest request
    ) {

        Service service = serviceRepository.findById(request.serviceId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Service not found: " + request.serviceId()
                        )
                );

        ServiceInstance instance = new ServiceInstance(
                service,
                request.instanceName(),
                request.hostName(),
                request.environment(),
                request.region()
        );

        return ServiceInstanceResponse.from(
                serviceInstanceRepository.save(instance)
        );
    }

    @Transactional(readOnly = true)
    public ServiceInstanceResponse getInstance(UUID id) {

        ServiceInstance instance =
                serviceInstanceRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Service instance not found: " + id
                                )
                        );

        return ServiceInstanceResponse.from(instance);
    }

    @Transactional(readOnly = true)
    public List<ServiceInstanceResponse> getInstancesByService(
            UUID serviceId
    ) {

        if (!serviceRepository.existsById(serviceId)) {
            throw new IllegalArgumentException(
                    "Service not found: " + serviceId
            );
        }

        return serviceInstanceRepository
                .findByServiceId(serviceId)
                .stream()
                .map(ServiceInstanceResponse::from)
                .toList();
    }
}