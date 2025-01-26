package com.starbank.star.rules.impl;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.repository.RecommendationRepository;
import com.starbank.star.rules.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet {
    private final RecommendationRepository repository;

    public SimpleCreditRuleSet(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDTO> apply(UUID userId) {
        double debitDeposits = repository.getTotalDepositAmountByType(userId.toString(), "DEBIT");
        double debitSpending = repository.getTotalSpendingAmountByType(userId.toString(), "DEBIT");
        boolean noCreditProducts = repository.getTotalDepositAmountByType(userId.toString(), "CREDIT") == 0;

        if (noCreditProducts && debitDeposits > debitSpending && debitSpending > 100000) {
            return Optional.of(new RecommendationDTO(
                    "Simple Credit",
                    "ab138afb-f3ba-4a93-b74f-0fcee86d447f",
                    "Откройте мир выгодных кредитов с нами!"
            ));
        }
        return Optional.empty();
    }
}
