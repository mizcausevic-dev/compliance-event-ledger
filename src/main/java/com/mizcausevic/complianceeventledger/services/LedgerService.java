package com.mizcausevic.complianceeventledger.services;

import com.mizcausevic.complianceeventledger.data.SampleLedgerData;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.ComplianceEvent;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.DashboardSummary;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.LedgerAnalysisInput;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.LedgerAnalysisResponse;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.Severity;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.TimelineView;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class LedgerService {

    private final List<ComplianceEvent> events = SampleLedgerData.events();

    public List<ComplianceEvent> getEvents() {
        return events.stream()
                .sorted(Comparator.comparing(ComplianceEvent::occurredAt).reversed())
                .toList();
    }

    public ComplianceEvent getEventById(String id) {
        return events.stream()
                .filter(event -> event.id().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
    }

    public TimelineView getTimeline(String entityId) {
        List<ComplianceEvent> matching = events.stream()
                .filter(event -> event.entityId().equalsIgnoreCase(entityId))
                .sorted(Comparator.comparing(ComplianceEvent::occurredAt))
                .toList();

        if (matching.isEmpty()) {
            throw new IllegalArgumentException("Entity timeline not found: " + entityId);
        }

        return new TimelineView(entityId, matching.getFirst().entityName(), matching);
    }

    public DashboardSummary getSummary() {
        LocalDateTime now = LocalDateTime.now();
        int criticalEvents = (int) events.stream().filter(event -> event.severity() == Severity.CRITICAL).count();
        int exceptionEvents = (int) events.stream().filter(event -> event.category().name().equals("EXCEPTION")).count();
        int overdueActions = (int) events.stream()
                .filter(event -> event.dueAt() != null && event.dueAt().isBefore(now) && !event.status().equalsIgnoreCase("Approved"))
                .count();
        List<String> lanes = events.stream().map(ComplianceEvent::ownerLane).distinct().sorted().toList();

        return new DashboardSummary(events.size(), criticalEvents, exceptionEvents, overdueActions, lanes);
    }

    public LedgerAnalysisResponse analyzeLedger(LedgerAnalysisInput input) {
        int score = 32;
        List<String> issues = new java.util.ArrayList<>();
        List<String> passedChecks = new java.util.ArrayList<>();

        if (input.highestSeverity() == Severity.CRITICAL) {
            score += 28;
            issues.add("Critical severity activity is attached to this entity.");
        } else if (input.highestSeverity() == Severity.HIGH) {
            score += 18;
            issues.add("High severity activity requires accelerated review.");
        } else {
            passedChecks.add("No high-severity event is currently attached.");
        }

        if (Boolean.TRUE.equals(input.hasOpenException())) {
            score += 16;
            issues.add("An exception is still open and requires ownership validation.");
        } else {
            passedChecks.add("No open exception is currently attached.");
        }

        if (Boolean.TRUE.equals(input.hasOverdueRemediation())) {
            score += 20;
            issues.add("Remediation deadlines have already slipped.");
        } else {
            passedChecks.add("No overdue remediation is currently attached.");
        }

        if (input.daysUntilReview() <= 7) {
            score += 14;
            issues.add("The next mandatory review is inside a seven-day window.");
        } else {
            passedChecks.add("The next review window still has workable lead time.");
        }

        Set<String> strongControls = Set.of("retention-policy", "approval-history", "owner-routing", "exception-register");
        long matchedControls = input.activeControls().stream().filter(strongControls::contains).count();
        if (matchedControls >= 2) {
            score -= 8;
            passedChecks.add("Control coverage is broad enough to reduce uncontrolled drift.");
        } else {
            issues.add("Control coverage is too thin for the current pressure profile.");
        }

        score = Math.max(5, Math.min(score, 97));
        String status = score >= 75 ? "escalate" : score >= 50 ? "watch" : "stable";
        String nextAction = switch (status) {
            case "escalate" ->
                    "Route to " + input.ownerLane() + " and audit leadership for same-day remediation sequencing.";
            case "watch" ->
                    "Keep the entity in the active ledger queue and confirm exception ownership before the next review window.";
            default ->
                    "Maintain normal monitoring cadence and document the next review checkpoint.";
        };

        return new LedgerAnalysisResponse(status, score, issues, passedChecks, nextAction);
    }
}
