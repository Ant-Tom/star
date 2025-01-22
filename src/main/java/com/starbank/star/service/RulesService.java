package com.starbank.star.service;

import com.starbank.star.cache.CacheService;
import com.starbank.star.entity.Rules;
import com.starbank.star.rules.RulesRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RulesService {

    private final RulesRepository rulesRepository;
    private final CacheService cacheService;

    public RulesService(RulesRepository rulesRepository, CacheService cacheService) {
        this.rulesRepository = rulesRepository;
        this.cacheService = cacheService;
    }

    public Rules addRule(Rules rule) {
        return rulesRepository.save(rule);
    }

    public void deleteRule(UUID ruleId) {
        rulesRepository.deleteById(ruleId);
    }

    public List<Rules> getAllRules() {
        return rulesRepository.findAll();
    }

    public Optional<Rules> getRuleById(UUID ruleId) {
        String cacheKey = ruleId.toString();
        Rules cachedRule = (Rules) cacheService.get(cacheKey);
        if (cachedRule != null) {
            return Optional.of(cachedRule);
        }

        Optional<Rules> rule = rulesRepository.findById(ruleId);
        rule.ifPresent(r -> cacheService.put(cacheKey, r));
        return rule;
    }
}