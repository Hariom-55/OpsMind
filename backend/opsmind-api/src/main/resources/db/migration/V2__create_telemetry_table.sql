
CREATE TABLE telemetry (
                           id UUID PRIMARY KEY,

                           service_instance_id UUID NOT NULL,

                           recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,

                           cpu_usage NUMERIC(5,2),
                           memory_usage NUMERIC(5,2),

                           request_count BIGINT,

                           response_time_ms NUMERIC(12,2),

                           error_rate NUMERIC(5,2),

                           database_connections INTEGER,

                           network_latency_ms NUMERIC(12,2),

                           availability NUMERIC(5,2),

                           CONSTRAINT fk_telemetry_service_instance
                               FOREIGN KEY (service_instance_id)
                                   REFERENCES service_instances(id)
                                   ON DELETE RESTRICT,

                           CONSTRAINT chk_telemetry_cpu_usage
                               CHECK (cpu_usage IS NULL OR (cpu_usage >= 0 AND cpu_usage <= 100)),

                           CONSTRAINT chk_telemetry_memory_usage
                               CHECK (memory_usage IS NULL OR (memory_usage >= 0 AND memory_usage <= 100)),

                           CONSTRAINT chk_telemetry_request_count
                               CHECK (request_count IS NULL OR request_count >= 0),

                           CONSTRAINT chk_telemetry_response_time
                               CHECK (response_time_ms IS NULL OR response_time_ms >= 0),

                           CONSTRAINT chk_telemetry_error_rate
                               CHECK (error_rate IS NULL OR (error_rate >= 0 AND error_rate <= 100)),

                           CONSTRAINT chk_telemetry_database_connections
                               CHECK (
                                   database_connections IS NULL
                                       OR database_connections >= 0
                                   ),

                           CONSTRAINT chk_telemetry_network_latency
                               CHECK (
                                   network_latency_ms IS NULL
                                       OR network_latency_ms >= 0
                                   ),

                           CONSTRAINT chk_telemetry_availability
                               CHECK (
                                   availability IS NULL
                                       OR (availability >= 0 AND availability <= 100)
                                   )
);

CREATE INDEX idx_telemetry_service_instance_recorded_at
    ON telemetry(service_instance_id, recorded_at);

CREATE INDEX idx_telemetry_recorded_at
    ON telemetry(recorded_at);