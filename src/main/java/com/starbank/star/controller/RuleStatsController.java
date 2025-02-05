package com.starbank.star.controller;

import com.starbank.star.entity.RuleStats;
import com.starbank.star.rules.RuleStatsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rule")
public class RuleStatsController {
    private final RuleStatsRepository statsRepository;

    public RuleStatsController(RuleStatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<RuleStats> stats = statsRepository.findAll();
        return Map.of("stats: ", stats.stream()
                .map(stat -> Map.of(
                        "rule_id ", stat.getRuleId(),
                        "count ", stat.getCount()
                )).toList());
    }
}
