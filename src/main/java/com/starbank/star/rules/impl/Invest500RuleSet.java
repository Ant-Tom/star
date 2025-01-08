package com.starbank.star.rules.impl;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.repository.RecommendationRepository;
import com.starbank.star.rules.RecommendationRuleSet;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RuleSet implements RecommendationRuleSet {
    private final RecommendationRepository repository;

    public Invest500RuleSet(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDTO> apply(UUID userId) {
        boolean usesDebit = repository.getTotalDepositAmountByType(userId.toString(), "DEBIT") > 0;
        boolean noInvestProducts = repository.getTotalDepositAmountByType(userId.toString(), "INVEST") == 0;
        double savingDeposits = repository.getTotalDepositAmountByType(userId.toString(), "SAVING");

        if (usesDebit && noInvestProducts && savingDeposits > 1000) {
            return Optional.of(new RecommendationDTO(
                    "Invest 500",
                    "147f6a0f-3b91-413b-ab99-87f081d60d5a",
                    "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка!"
            ));
        }
        return Optional.empty();
    }
}
