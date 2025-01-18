package com.starbank.star.controller;

import com.starbank.star.entity.Rules;
import com.starbank.star.service.RulesService;
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

    @PostMapping
    public ResponseEntity<Rules> addRule(@RequestBody Rules rule) {
        return ResponseEntity.ok(rulesService.addRule(rule));
    }

    @GetMapping
    public ResponseEntity<List<Rules>> getAllRules() {
        return ResponseEntity.ok(rulesService.getAllRules());
    }

    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Void> deleteRule(@PathVariable UUID ruleId) {
        rulesService.deleteRule(ruleId);
        return ResponseEntity.noContent().build();
    }
}
