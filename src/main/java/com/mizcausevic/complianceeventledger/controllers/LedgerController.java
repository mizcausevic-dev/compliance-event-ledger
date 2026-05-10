package com.mizcausevic.complianceeventledger.controllers;

import com.mizcausevic.complianceeventledger.models.ComplianceModels.DashboardSummary;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.HealthResponse;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.LedgerAnalysisInput;
import com.mizcausevic.complianceeventledger.models.ComplianceModels.LedgerAnalysisResponse;
import com.mizcausevic.complianceeventledger.services.LedgerService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LedgerController {

    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @GetMapping("/")
    public HealthResponse root() {
        return new HealthResponse("ok", "compliance-event-ledger", "/docs");
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @GetMapping("/api/events")
    public ResponseEntity<?> getEvents() {
        return ResponseEntity.ok(ledgerService.getEvents());
    }

    @GetMapping("/api/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(ledgerService.getEventById(id));
    }

    @GetMapping("/api/timeline/{entityId}")
    public ResponseEntity<?> getTimeline(@PathVariable String entityId) {
        return ResponseEntity.ok(ledgerService.getTimeline(entityId));
    }

    @GetMapping("/api/dashboard/summary")
    public DashboardSummary getSummary() {
        return ledgerService.getSummary();
    }

    @PostMapping("/api/analyze/ledger")
    public LedgerAnalysisResponse analyzeLedger(@Valid @RequestBody LedgerAnalysisInput input) {
        return ledgerService.analyzeLedger(input);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleNotFound(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", exception.getMessage()));
    }
}
