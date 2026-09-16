package com.opsmind.api.telemetry.controller;

import com.opsmind.api.telemetry.dto.CreateTelemetryRequest;
import com.opsmind.api.telemetry.dto.TelemetryResponse;
import com.opsmind.api.telemetry.service.TelemetryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(
            TelemetryService telemetryService
    ) {
        this.telemetryService = telemetryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TelemetryResponse recordTelemetry(
            @Valid @RequestBody CreateTelemetryRequest request
    ) {
        return telemetryService.recordTelemetry(request);
    }

    @GetMapping("/service-instance/{serviceInstanceId}")
    public List<TelemetryResponse> getLatestTelemetry(
            @PathVariable UUID serviceInstanceId
    ) {
        return telemetryService.getLatestTelemetry(
                serviceInstanceId
        );
    }

    @GetMapping("/service-instance/{serviceInstanceId}/range")
    public List<TelemetryResponse> getTelemetryBetween(
            @PathVariable UUID serviceInstanceId,
            @RequestParam OffsetDateTime start,
            @RequestParam OffsetDateTime end
    ) {
        return telemetryService.getTelemetryBetween(
                serviceInstanceId,
                start,
                end
        );
    }
}