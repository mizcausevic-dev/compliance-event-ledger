package com.mizcausevic.complianceeventledger.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public final class ComplianceModels {

    private ComplianceModels() {
    }

    public enum EventCategory {
        POLICY_ACTION,
        APPROVAL,
        EXCEPTION,
        REMEDIATION,
        REVIEW,
        ALERT
    }

    public enum Severity {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    public record ComplianceEvent(
            String id,
            String entityId,
            String entityName,
            EventCategory category,
            Severity severity,
            String ownerLane,
            String status,
            String title,
            String summary,
            List<String> tags,
            LocalDateTime occurredAt,
            LocalDateTime dueAt) {
    }

    public record DashboardSummary(
            int totalEvents,
            int criticalEvents,
            int exceptionEvents,
            int overdueActions,
            List<String> ownershipLanes) {
    }

    public record TimelineView(
            String entityId,
            String entityName,
            List<ComplianceEvent> events) {
    }

    public record LedgerAnalysisInput(
            @NotBlank String entityName,
            @NotBlank String ownerLane,
            @NotNull Severity highestSeverity,
            @NotNull Integer daysUntilReview,
            @NotNull Boolean hasOpenException,
            @NotNull Boolean hasOverdueRemediation,
            @NotEmpty List<String> activeControls) {
    }

    public record LedgerAnalysisResponse(
            String status,
            int score,
            List<String> issues,
            List<String> passedChecks,
            String recommendedNextAction) {
    }

    public record HealthResponse(
            String status,
            String service,
            String docs) {
    }
}
