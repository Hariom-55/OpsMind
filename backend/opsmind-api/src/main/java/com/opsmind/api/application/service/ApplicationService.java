package com.opsmind.api.application.service;

import com.opsmind.api.application.dto.ApplicationResponse;
import com.opsmind.api.application.dto.CreateApplicationRequest;
import com.opsmind.api.application.entity.Application;
import com.opsmind.api.application.repository.ApplicationRepository;
import com.opsmind.api.customer.entity.Customer;
import com.opsmind.api.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CustomerRepository customerRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CustomerRepository customerRepository
    ) {
        this.applicationRepository = applicationRepository;
        this.customerRepository = customerRepository;
    }

    public ApplicationResponse createApplication(
            CreateApplicationRequest request
    ) {

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found: " + request.customerId()
                        )
                );

        Application application = new Application(
                customer,
                request.name(),
                request.description(),
                request.environment()
        );

        return ApplicationResponse.from(
                applicationRepository.save(application)
        );
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByCustomer(
            UUID customerId
    ) {

        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer not found: " + customerId
            );
        }

        return applicationRepository.findByCustomerId(customerId)
                .stream()
                .map(ApplicationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplication(UUID id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Application not found: " + id
                        )
                );

        return ApplicationResponse.from(application);
    }
}