package com.mizcausevic.complianceeventledger.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mizcausevic.complianceeventledger.models.ComplianceModels.LedgerAnalysisInput;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.Severity;
import java.util.List;
import org.junit.jupiter.api.Test;

class LedgerServiceTests {

    private final LedgerService ledgerService = new LedgerService();

    @Test
    void summaryIncludesExpectedEventCount() {
        assertEquals(6, ledgerService.getSummary().totalEvents());
        assertTrue(ledgerService.getSummary().criticalEvents() >= 2);
    }

    @Test
    void analysisEscalatesCriticalOverdueEntity() {
        var response = ledgerService.analyzeLedger(new LedgerAnalysisInput(
                "Acme Data Processor",
                "Compliance Operations",
                Severity.CRITICAL,
                4,
                true,
                true,
                List.of("retention-policy", "approval-history")));

        assertEquals("escalate", response.status());
        assertTrue(response.score() >= 75);
    }
}
