# OpsMind

**AI-Powered Service Operations & Risk Intelligence Platform**

OpsMind is a full-stack platform designed to detect service degradation, predict operational incidents, investigate root causes using telemetry and runbooks, and provide evidence-backed remediation recommendations with human approval.

## Core Architecture

```text
Service Telemetry
        ↓
SLA / Anomaly Detection
        ↓
Incident
        ↓
Context Collection
        ↓
ML Risk Prediction
        ↓
Runbook / SOP Retrieval
        ↓
AI Agent Investigation
        ↓
Evidence-backed Recommendation
        ↓
Engineer Approval
        ↓
Audit Trail
```

## Technology Stack

- **Backend:** Java 21, Spring Boot 4.1.x, Maven
- **Database:** PostgreSQL, Flyway, pgvector (planned)
- **AI/ML Service:** Python, FastAPI
- **ML:** scikit-learn / XGBoost (planned)
- **RAG:** Runbook/SOP knowledge base (planned)
- **AI Agent:** Tool-based, evidence-driven, human-in-the-loop (planned)
- **Caching / Rate Limiting:** Redis (planned)
- **Event Streaming:** Kafka (planned)
- **Frontend:** React / Next.js (planned)
- **Deployment:** Docker (planned)

## Repository Structure

```text
OpsMind/
├── backend/
│   └── opsmind-api/
├── ai-service/
│   └── opsmind-ai/
├── frontend/
├── database/
│   ├── migrations/
│   ├── seed/
│   └── views/
├── data/
│   ├── raw/
│   ├── processed/
│   └── sample/
├── docs/
└── infrastructure/
    └── docker/
```

## Documentation

The `docs/` directory contains the pre-implementation product, architecture, database, API, ML, RAG, agent, security, testing, deployment, and implementation-roadmap documents.

### Documentation Order

| # | Document |
|---|---|
| 00 | Documentation Index |
| 01 | Product Definition & Vision |
| 02 | PRD / Functional / Non-Functional Requirements |
| 03 | Business Use Cases & Workflows |
| 04 | Dataset & Data Dictionary |
| 05 | High-Level System Architecture |
| 06 | Java Low-Level Design |
| 07 | PostgreSQL Database Design |
| 08 | API Contracts & Integration Specification |
| 09 | ML Risk Prediction Design |
| 10 | RAG / Runbook Knowledge Architecture |
| 11 | AI Agent Design & Guardrails |
| 12 | Security / Threat Model / Access Control |
| 13 | Testing / QA / AI Evaluation Strategy |
| 14 | Deployment / Operations / Observability |
| 15 | Implementation Roadmap / Definition of Done |

## Engineering Principles

1. Java/Spring Boot owns the core application and business workflow.
2. Python/FastAPI owns ML, RAG, and AI intelligence.
3. PostgreSQL is the operational source of truth.
4. Flyway owns database schema evolution.
5. AI recommendations must be evidence-backed.
6. Production-changing actions require explicit human approval.
7. No unrestricted SQL or autonomous production modifications for the AI agent.
8. Build the deterministic operational foundation before adding the LLM.

## Current Implementation Status

- Project repository initialized
- Java/Spring Boot backend initialized
- Python/FastAPI AI service initialized
- PostgreSQL `opsmind` database created
- Flyway integrated
- V1 core domain migration applied successfully
- Core tables created: `users`, `customers`, `applications`, `services`, `service_instances`

## Build Sequence

```text
Foundation
   ↓
Core Domain + Database
   ↓
Telemetry
   ↓
Deterministic Incident Detection
   ↓
ML Risk Prediction
   ↓
RAG / Runbooks
   ↓
AI Agent
   ↓
Frontend
   ↓
Security + Observability + Production Hardening
```

## Golden End-to-End Scenario

```text
Service degradation
→ Incident detection
→ ML risk prediction
→ Runbook retrieval
→ AI investigation
→ Evidence-backed recommendation
→ Human approval
→ Audit
```
