package com.opsmind.api.serviceinstance.repository;

import com.opsmind.api.serviceinstance.entity.ServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceInstanceRepository extends JpaRepository<ServiceInstance, UUID> {

    List<ServiceInstance> findByServiceId(UUID serviceId);
}