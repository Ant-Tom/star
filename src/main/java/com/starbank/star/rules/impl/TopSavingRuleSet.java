package com.starbank.star.rules.impl;


import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.repository.RecommendationRepository;
import com.starbank.star.rules.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet {
    private final RecommendationRepository repository;

    public TopSavingRuleSet(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDTO> apply(UUID userId) {
        double debitDeposits = repository.getTotalDepositAmountByType(userId.toString(), "DEBIT");
        double savingDeposits = repository.getTotalDepositAmountByType(userId.toString(), "SAVING");
        double debitSpending = repository.getTotalSpendingAmountByType(userId.toString(), "DEBIT");

        if ((debitDeposits >= 50000 || savingDeposits >= 50000) && debitDeposits > debitSpending) {
            return Optional.of(new RecommendationDTO(
                    "Top Saving",
                    "59efc529-2fff-41af-baff-90ccd7402925",
                    "Откройте свою собственную «Копилку» с нашим банком!"
            ));
        }
        return Optional.empty();
    }
}
