package com.opsmind.api.incident.repository;

import com.opsmind.api.incident.entity.Incident;
import com.opsmind.api.incident.entity.IncidentStatus;
import com.opsmind.api.incident.entity.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IncidentRepository
        extends JpaRepository<Incident, UUID> {

    List<Incident> findByServiceInstanceIdOrderByDetectedAtDesc(
            UUID serviceInstanceId
    );

    List<Incident> findByStatusOrderByDetectedAtDesc(
            IncidentStatus status
    );

    boolean existsByServiceInstanceIdAndTypeAndStatusIn(
            UUID serviceInstanceId,
            IncidentType type,
            List<IncidentStatus> statuses
    );
}