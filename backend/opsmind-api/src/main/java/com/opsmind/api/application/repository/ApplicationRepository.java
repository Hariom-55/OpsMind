package com.opsmind.api.application.repository;

import com.opsmind.api.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findByCustomerId(UUID customerId);
}