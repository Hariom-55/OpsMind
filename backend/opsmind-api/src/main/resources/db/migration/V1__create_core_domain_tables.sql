
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name VARCHAR(150) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT chk_users_role
                           CHECK (role IN ('ADMIN', 'OPS_ENGINEER', 'ANALYST', 'VIEWER'))
);



CREATE TABLE customers (
                           id UUID PRIMARY KEY,
                           name VARCHAR(200) NOT NULL,
                           external_reference VARCHAR(100) UNIQUE,
                           contact_email VARCHAR(255),
                           active BOOLEAN NOT NULL DEFAULT TRUE,
                           created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE applications (
                              id UUID PRIMARY KEY,
                              customer_id UUID NOT NULL,
                              name VARCHAR(200) NOT NULL,
                              description TEXT,
                              environment VARCHAR(50) NOT NULL,
                              active BOOLEAN NOT NULL DEFAULT TRUE,
                              created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

                              CONSTRAINT fk_applications_customer
                                  FOREIGN KEY (customer_id)
                                      REFERENCES customers(id)
                                      ON DELETE RESTRICT,

                              CONSTRAINT uq_application_customer_name
                                  UNIQUE (customer_id, name)
);



CREATE TABLE services (
                          id UUID PRIMARY KEY,
                          application_id UUID NOT NULL,
                          name VARCHAR(200) NOT NULL,
                          service_type VARCHAR(100),
                          description TEXT,
                          criticality VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
                          sla_target NUMERIC(5,2),
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_services_application
                              FOREIGN KEY (application_id)
                                  REFERENCES applications(id)
                                  ON DELETE RESTRICT,

                          CONSTRAINT uq_service_application_name
                              UNIQUE (application_id, name),

                          CONSTRAINT chk_services_criticality
                              CHECK (criticality IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),

                          CONSTRAINT chk_services_sla_target
                              CHECK (sla_target IS NULL OR (sla_target >= 0 AND sla_target <= 100))
);



CREATE TABLE service_instances (
                                   id UUID PRIMARY KEY,
                                   service_id UUID NOT NULL,
                                   instance_name VARCHAR(200) NOT NULL,
                                   host_name VARCHAR(255),
                                   environment VARCHAR(50) NOT NULL,
                                   region VARCHAR(100),
                                   status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
                                   created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                   CONSTRAINT fk_service_instances_service
                                       FOREIGN KEY (service_id)
                                           REFERENCES services(id)
                                           ON DELETE RESTRICT,

                                   CONSTRAINT uq_service_instance_name
                                       UNIQUE (service_id, instance_name),

                                   CONSTRAINT chk_service_instance_status
                                       CHECK (status IN ('ACTIVE', 'INACTIVE', 'DEGRADED', 'TERMINATED'))
);




CREATE INDEX idx_applications_customer_id
    ON applications(customer_id);

CREATE INDEX idx_services_application_id
    ON services(application_id);

CREATE INDEX idx_service_instances_service_id
    ON service_instances(service_id);

CREATE INDEX idx_users_email
    ON users(email);