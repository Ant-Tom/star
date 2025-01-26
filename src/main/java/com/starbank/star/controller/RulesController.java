package com.starbank.star.controller;

import com.starbank.star.entity.Rules;
import com.starbank.star.service.RulesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rules")
public class RulesController {

    private final RulesService rulesService;

    public RulesController(RulesService rulesService) {
        this.rulesService = rulesService;
    }

    @Operation(summary = "Get all rules", description = "Retrieve all recommendation rules from the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved rules")
    @GetMapping
    public ResponseEntity<List<Rules>> getAllRules() {
        return ResponseEntity.ok(rulesService.getAllRules());
    }

    @Operation(summary = "Add a new rule", description = "Create a new recommendation rule")
    @ApiResponse(responseCode = "201", description = "Rule created successfully")
    @PostMapping
    public ResponseEntity<Rules> addRule(@RequestBody Rules rule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rulesService.addRule(rule));
    }

    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID ruleId) {
        if (!rulesService.getRuleById(ruleId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rulesService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }
}