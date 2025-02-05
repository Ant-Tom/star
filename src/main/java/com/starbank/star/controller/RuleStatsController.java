package com.starbank.star.controller;

import com.starbank.star.entity.Rules;
import com.starbank.star.rules.RuleStatsRepository;
import com.starbank.star.rules.RulesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rule")
public class RuleStatsController {

    private final RuleStatsRepository ruleStatsRepository;
    private final RulesRepository rulesRepository;

    public RuleStatsController(RuleStatsRepository ruleStatsRepository, RulesRepository rulesRepository) {
        this.ruleStatsRepository = ruleStatsRepository;
        this.rulesRepository = rulesRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        // Получаем все динамические правила
        List<Rules> allRules = rulesRepository.findAll();
        // Для каждого правила получаем статистику (если записи нет – ставим 0)
        List<Map<String, Object>> statsList = allRules.stream().map(rule -> {
            String ruleId = rule.getId().toString();
            int count = ruleStatsRepository.findById(ruleId)
                    .map(stat -> stat.getCount())
                    .orElse(0);
            Map<String, Object> map = new HashMap<>();
            map.put("rule_id", ruleId);
            map.put("count", count);
            return map;
        }).collect(Collectors.toList());
        return Map.of("stats", statsList);
    }
}
