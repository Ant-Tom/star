package com.starbank.star.service;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.cache.CacheService;
import com.starbank.star.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;
    private final CacheService cacheService;

    public RecommendationService(List<RecommendationRuleSet> ruleSets, CacheService cacheService) {
        this.ruleSets = ruleSets;
        this.cacheService = cacheService;
    }

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        String cacheKey = userId.toString();
        List<RecommendationDTO> cachedRecommendations = (List<RecommendationDTO>) cacheService.get(cacheKey);
        if (cachedRecommendations != null) {
            return cachedRecommendations;
        }

        List<RecommendationDTO> recommendations = ruleSets.stream()
                .map(rule -> rule.apply(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        cacheService.put(cacheKey, recommendations.isEmpty() ? Collections.emptyList() : recommendations);
        return recommendations;
    }
}
