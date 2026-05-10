package com.mizcausevic.complianceeventledger.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LedgerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void rootReturnsServiceInfo() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("compliance-event-ledger"));
    }

    @Test
    void dashboardSummaryReturnsTotals() throws Exception {
        mockMvc.perform(get("/api/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEvents").value(6));
    }

    @Test
    void analyzeRouteReturnsScoreAndStatus() throws Exception {
        mockMvc.perform(post("/api/analyze/ledger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "entityName": "AI Policy Operations",
                                  "ownerLane": "Model Risk",
                                  "highestSeverity": "CRITICAL",
                                  "daysUntilReview": 3,
                                  "hasOpenException": true,
                                  "hasOverdueRemediation": false,
                                  "activeControls": ["approval-history", "exception-register"]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.score").isNumber());
    }
}
