package com.mizcausevic.complianceeventledger.data;

import com.mizcausevic.complianceeventledger.models.ComplianceModels.ComplianceEvent;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.EventCategory;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.Severity;
import java.time.LocalDateTime;
import java.util.List;

public final class SampleLedgerData {

    private SampleLedgerData() {
    }

    public static List<ComplianceEvent> events() {
        return List.of(
                new ComplianceEvent(
                        "evt-1001",
                        "vendor-risk-acme",
                        "Acme Data Processor",
                        EventCategory.EXCEPTION,
                        Severity.HIGH,
                        "Compliance Operations",
                        "Open",
                        "Retention policy exception approved with 30-day follow-up",
                        "Vendor onboarding completed, but legal approved a temporary retention exception until the sub-processor review closes.",
                        List.of("vendor-risk", "retention", "legal"),
                        LocalDateTime.of(2026, 5, 2, 9, 30),
                        LocalDateTime.of(2026, 5, 28, 17, 0)),
                new ComplianceEvent(
                        "evt-1002",
                        "vendor-risk-acme",
                        "Acme Data Processor",
                        EventCategory.REMEDIATION,
                        Severity.CRITICAL,
                        "Security Engineering",
                        "Overdue",
                        "Encryption key rotation still incomplete",
                        "The compensating control is documented, but the permanent remediation missed its deadline and now blocks production certification.",
                        List.of("security", "encryption", "remediation"),
                        LocalDateTime.of(2026, 5, 5, 11, 0),
                        LocalDateTime.of(2026, 5, 9, 12, 0)),
                new ComplianceEvent(
                        "evt-1003",
                        "finance-close-q2",
                        "Q2 Finance Close Workflow",
                        EventCategory.APPROVAL,
                        Severity.MEDIUM,
                        "Finance Controls",
                        "Approved",
                        "Journal approval path updated for controller review",
                        "The approval chain was shortened after identifying duplicate finance-review hops that did not add control value.",
                        List.of("finance", "approval", "close"),
                        LocalDateTime.of(2026, 5, 4, 13, 15),
                        LocalDateTime.of(2026, 5, 18, 17, 0)),
                new ComplianceEvent(
                        "evt-1004",
                        "finance-close-q2",
                        "Q2 Finance Close Workflow",
                        EventCategory.REVIEW,
                        Severity.HIGH,
                        "Internal Audit",
                        "Scheduled",
                        "Quarterly access review nearing deadline",
                        "The workflow is stable, but the linked privileged access review is inside a seven-day window and must be closed before sign-off.",
                        List.of("audit", "access-review", "finance"),
                        LocalDateTime.of(2026, 5, 7, 10, 0),
                        LocalDateTime.of(2026, 5, 16, 17, 0)),
                new ComplianceEvent(
                        "evt-1005",
                        "ai-policy-ops",
                        "AI Policy Operations",
                        EventCategory.ALERT,
                        Severity.CRITICAL,
                        "Model Risk",
                        "Escalated",
                        "Policy-sensitive prompt path triggered executive review",
                        "The release train is paused until model-risk and privacy sign off on the latest prompt-classification change.",
                        List.of("ai-governance", "privacy", "model-risk"),
                        LocalDateTime.of(2026, 5, 8, 14, 45),
                        LocalDateTime.of(2026, 5, 12, 9, 0)),
                new ComplianceEvent(
                        "evt-1006",
                        "ai-policy-ops",
                        "AI Policy Operations",
                        EventCategory.POLICY_ACTION,
                        Severity.MEDIUM,
                        "Platform Governance",
                        "In Progress",
                        "Prompt logging policy updated for sensitive domains",
                        "The policy baseline now requires redaction and approval metadata for healthcare and finance prompts.",
                        List.of("policy", "logging", "sensitive-domains"),
                        LocalDateTime.of(2026, 5, 3, 16, 30),
                        LocalDateTime.of(2026, 5, 14, 17, 0))
        );
    }
}
