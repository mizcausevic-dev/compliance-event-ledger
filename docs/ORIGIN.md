# Why This Exists

This repository models a simple question for compliance operations: if policy actions, approvals, exceptions, and remediations share an event format, can a reviewer find an entity's sequence and current pressure quickly?

The Java service provides read-only API routes over six synthetic records and a scoring function over caller-supplied inputs. It is useful for reviewing the shape of an event timeline and the clarity of a pressure recommendation. It does not accept new events, store an immutable history, or supply audit-grade evidence.

A production ledger would need authenticated write and read boundaries, append-only storage, source attribution, timestamps with defined time zones, tamper evidence, retention and deletion policy, and real operational validation. The current service stays loopback-bound by default and its OpenAPI description labels the synthetic fixture.
