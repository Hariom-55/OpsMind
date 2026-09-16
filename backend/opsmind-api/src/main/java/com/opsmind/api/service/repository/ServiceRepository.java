package com.opsmind.api.service.repository;

import com.opsmind.api.service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {

    List<Service> findByApplicationId(UUID applicationId);
}