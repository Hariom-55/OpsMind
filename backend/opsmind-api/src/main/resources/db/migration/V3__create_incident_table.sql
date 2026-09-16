
CREATE TABLE incidents (
                           id UUID PRIMARY KEY,

                           service_instance_id UUID NOT NULL,

                           incident_type VARCHAR(50) NOT NULL,
                           severity VARCHAR(50) NOT NULL,
                           status VARCHAR(50) NOT NULL,

                           title VARCHAR(255) NOT NULL,
                           description TEXT,

                           metric_name VARCHAR(100),
                           metric_value NUMERIC(12,2),
                           threshold_value NUMERIC(12,2),

                           detected_at TIMESTAMP WITH TIME ZONE NOT NULL,
                           resolved_at TIMESTAMP WITH TIME ZONE,

                           CONSTRAINT fk_incidents_service_instance
                               FOREIGN KEY (service_instance_id)
                                   REFERENCES service_instances(id)
                                   ON DELETE RESTRICT,

                           CONSTRAINT chk_incidents_type
                               CHECK (
                                   incident_type IN (
                                                     'HIGH_CPU',
                                                     'HIGH_MEMORY',
                                                     'HIGH_LATENCY',
                                                     'HIGH_ERROR_RATE',
                                                     'SLA_BREACH'
                                       )
                                   ),

                           CONSTRAINT chk_incidents_severity
                               CHECK (
                                   severity IN (
                                                'LOW',
                                                'MEDIUM',
                                                'HIGH',
                                                'CRITICAL'
                                       )
                                   ),

                           CONSTRAINT chk_incidents_status
                               CHECK (
                                   status IN (
                                              'DETECTED',
                                              'ANALYZING',
                                              'ACTION_REQUIRED',
                                              'ENGINEER_REVIEW',
                                              'ACTION_APPROVED',
                                              'RESOLVED'
                                       )
                                   )
);

CREATE INDEX idx_incidents_service_instance
    ON incidents(service_instance_id);

CREATE INDEX idx_incidents_detected_at
    ON incidents(detected_at);

CREATE INDEX idx_incidents_status
    ON incidents(status);