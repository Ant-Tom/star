package com.starbank.star;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.entity.RuleQuery;
import com.starbank.star.entity.Rules;
import com.starbank.star.repository.RecommendationRepository;
import com.starbank.star.rules.RecommendationRuleSet;
import com.starbank.star.rules.RuleStatsRepository;
import com.starbank.star.service.RecommendationService;
import com.starbank.star.service.RulesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    @Mock
    private List<RecommendationRuleSet> ruleSets;

    @Mock
    private RulesService rulesService;

    @Mock
    private RecommendationRepository repository;

    @Mock
    private RuleStatsRepository ruleStatsRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendations() {
        UUID userId = UUID.randomUUID();

        // Мокируем статические правила
        RecommendationRuleSet staticRuleSet = user -> Optional.of(new RecommendationDTO("Static Rule", "static-id", "Static Description"));
        when(ruleSets.stream()).thenReturn(List.of(staticRuleSet).stream());

        // Создаем динамическое правило
        Rules rule = new Rules();
        rule.setId(UUID.randomUUID()); // Устанавливаем ID, чтобы избежать NullPointerException
        rule.setProductName("Dynamic Rule");
        rule.setProductId("dynamic-id");
        rule.setProductText("Dynamic Description");
        rule.setRuleQueries(List.of(new RuleQuery("USER_OF", List.of("CREDIT"), false)));
        when(rulesService.getAllRules()).thenReturn(List.of(rule));

        // Мокируем репозиторий
        when(repository.getTotalDepositAmountByType(userId.toString(), "CREDIT")).thenReturn(1000.0);

        // Запускаем тестируемый метод
        List<RecommendationDTO> recommendations = recommendationService.getRecommendations(userId);

        // Проверяем результат
        assertEquals(2, recommendations.size());
        assertEquals("Static Rule", recommendations.get(0).getName());
        assertEquals("Dynamic Rule", recommendations.get(1).getName());
    }
}
