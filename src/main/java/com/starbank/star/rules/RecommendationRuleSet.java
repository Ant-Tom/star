package com.starbank.star.rules;

import com.starbank.star.DTO.RecommendationDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RecommendationDTO> apply(UUID userId);
}
