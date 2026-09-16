package com.opsmind.api.service.service;

import com.opsmind.api.application.entity.Application;
import com.opsmind.api.application.repository.ApplicationRepository;
import com.opsmind.api.service.dto.CreateServiceRequest;
import com.opsmind.api.service.dto.ServiceResponse;
import com.opsmind.api.service.entity.Service;
import com.opsmind.api.service.entity.ServiceCriticality;
import com.opsmind.api.service.repository.ServiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class ServiceManagementService {

    private final ServiceRepository serviceRepository;
    private final ApplicationRepository applicationRepository;

    public ServiceManagementService(
            ServiceRepository serviceRepository,
            ApplicationRepository applicationRepository
    ) {
        this.serviceRepository = serviceRepository;
        this.applicationRepository = applicationRepository;
    }

    public ServiceResponse createService(
            CreateServiceRequest request
    ) {

        Application application =
                applicationRepository.findById(request.applicationId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Application not found: "
                                                + request.applicationId()
                                )
                        );

        ServiceCriticality criticality =
                request.criticality() != null
                        ? request.criticality()
                        : ServiceCriticality.MEDIUM;

        Service service = new Service(
                application,
                request.name(),
                request.serviceType(),
                request.description(),
                criticality,
                request.slaTarget()
        );

        return ServiceResponse.from(
                serviceRepository.save(service)
        );
    }

    @Transactional(readOnly = true)
    public ServiceResponse getService(UUID id) {

        Service service = serviceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Service not found: " + id
                        )
                );

        return ServiceResponse.from(service);
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> getServicesByApplication(
            UUID applicationId
    ) {

        if (!applicationRepository.existsById(applicationId)) {
            throw new IllegalArgumentException(
                    "Application not found: " + applicationId
            );
        }

        return serviceRepository.findByApplicationId(applicationId)
                .stream()
                .map(ServiceResponse::from)
                .toList();
    }
}