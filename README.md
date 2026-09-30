# Compliance Event Ledger

Compliance Event Ledger is a Java and Spring Boot **read-only sample service** for exploring policy actions, approvals, exceptions, remediations, and review pressure in a time-ordered event list. It uses six bundled synthetic records in memory. It does not ingest, persist, or prove the integrity of real audit events.

## Executive Summary

This project shows how governance data could be structured as an operational timeline. Each sample event has severity, ownership lane, tags, due date, and status so users can retrieve a sample event, inspect an entity timeline, summarize the fixture, and run a pressure analysis on supplied inputs.

## Portfolio Takeaway

- Java and Spring Boot backend with opinionated governance domain modeling
- timeline retrieval for audit review and entity-centric investigation
- severity-aware analysis route for escalation and next-step sequencing
- clean docs, tests, CI, and public proof assets

## Overview

| Area | Details |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.0.6 |
| API Docs | Swagger UI at `/docs` |
| Test Stack | JUnit 5, Spring Boot Test, MockMvc |
| Runtime Shape | Read-only synthetic event fixture with analysis service; loopback bind by default |
| Core Domains | policy actions, approvals, exceptions, remediations, reviews, alerts |

## API Surface

- `GET /`
- `GET /health`
- `GET /api/events`
- `GET /api/events/{id}`
- `GET /api/timeline/{entityId}`
- `GET /api/dashboard/summary`
- `POST /api/analyze/ledger`
- `GET /docs`

## Request Flow

```mermaid
flowchart LR
  A["Compliance event enters ledger"] --> B["Severity and owner lane captured"]
  B --> C["Entity timeline aggregates prior actions"]
  C --> D["Dashboard summary measures open pressure"]
  D --> E["Ledger analysis route scores urgency"]
  E --> F["Ops, audit, or leadership next action"]
```

## Analysis Logic

The analysis route treats governance pressure as a weighted combination of:

- highest active severity
- open exceptions
- overdue remediation work
- days until the next required review
- control coverage strength

That produces a score, status, issues, passed checks, and a recommended next action.

## Sample Analysis

Request:

```json
{
  "entityName": "AI Policy Operations",
  "ownerLane": "Model Risk",
  "highestSeverity": "CRITICAL",
  "daysUntilReview": 3,
  "hasOpenException": true,
  "hasOverdueRemediation": false,
  "activeControls": [
    "approval-history",
    "exception-register"
  ]
}
```

Response:

```json
{
  "status": "escalate",
  "score": 82,
  "issues": [
    "Critical severity activity is attached to this entity.",
    "An exception is still open and requires ownership validation.",
    "The next mandatory review is inside a seven-day window."
  ],
  "passedChecks": [
    "No overdue remediation is currently attached.",
    "Control coverage is broad enough to reduce uncontrolled drift."
  ],
  "recommendedNextAction": "Route to Model Risk and audit leadership for same-day remediation sequencing."
}
```

## Running service capture

The image below is a browser capture of this project's locally running `/docs` route on 2026-09-29. The API serves synthetic sample records. The prior static marketing mockups were removed because they showed stale counts and implied a user interface that this backend does not serve.

![Swagger UI for the locally running sample ledger API, with its synthetic-data disclosure](./screenshots/01-api-docs.png)

## Run Locally

```powershell
cd compliance-event-ledger
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

Then open:

- `http://127.0.0.1:4311/`
- `http://127.0.0.1:4311/docs`

If that port is already occupied, choose another one before running:

```powershell
$env:PORT = "4315"
.\mvnw.cmd spring-boot:run
```

## Validation

```powershell
cd compliance-event-ledger
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd test
.\mvnw.cmd package
```

## Tech Stack

[![Java 21](https://img.shields.io/badge/Java-21-0f172a?style=for-the-badge&logo=openjdk&logoColor=f8fafc)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-0f172a?style=for-the-badge&logo=springboot&logoColor=7ee787)](https://spring.io/projects/spring-boot)
[![Swagger](https://img.shields.io/badge/OpenAPI-Docs-0f172a?style=for-the-badge&logo=swagger&logoColor=85ea2d)](https://swagger.io/specification/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-0f172a?style=for-the-badge&logo=junit5&logoColor=facc15)](https://junit.org/junit5/)
[![Maven](https://img.shields.io/badge/Maven-Wrapper-0f172a?style=for-the-badge&logo=apachemaven&logoColor=f8fafc)](https://maven.apache.org/)

## Portfolio Links

- [Kinetic Gain](https://kineticgain.com/)
- [LinkedIn](https://www.linkedin.com/in/mirzacausevic)
- [Skills Page](https://mizcausevic.com/skills/)
- [Medium](https://medium.com/@mizcausevic)
- [GitHub](https://github.com/mizcausevic-dev)
