package com.opsmind.api.customer.service;

import com.opsmind.api.customer.dto.CreateCustomerRequest;
import com.opsmind.api.customer.dto.CustomerResponse;
import com.opsmind.api.customer.entity.Customer;
import com.opsmind.api.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Customer with this name already exists"
            );
        }

        Customer customer = new Customer(
                request.name(),
                request.externalReference(),
                request.contactEmail()
        );

        return CustomerResponse.from(
                customerRepository.save(customer)
        );
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(UUID id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found: " + id
                        )
                );

        return CustomerResponse.from(customer);
    }
}