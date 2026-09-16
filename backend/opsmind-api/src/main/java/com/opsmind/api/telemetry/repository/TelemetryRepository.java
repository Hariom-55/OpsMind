package com.opsmind.api.telemetry.repository;

import com.opsmind.api.telemetry.entity.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface TelemetryRepository
        extends JpaRepository<Telemetry, UUID> {

    List<Telemetry> findByServiceInstanceIdOrderByRecordedAtDesc(
            UUID serviceInstanceId
    );

    List<Telemetry> findByServiceInstanceIdAndRecordedAtBetweenOrderByRecordedAtAsc(
            UUID serviceInstanceId,
            OffsetDateTime start,
            OffsetDateTime end
    );
}