package com.starbank.star.repository;

import com.starbank.star.cache.CacheService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CacheService cacheService;

    public RecommendationRepository(JdbcTemplate jdbcTemplate, CacheService cacheService) {
        this.jdbcTemplate = jdbcTemplate;
        this.cacheService = cacheService;
    }

    public Double getTotalDepositAmountByType(String userId, String productType) {
        String cacheKey = "deposit_" + userId + "_" + productType;
        Double cachedValue = cacheService.getTransactionSum(cacheKey);
        if (cachedValue != null) {
            return cachedValue;
        }

        String sql = "SELECT SUM(t.amount) FROM user_transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND t.type = 'DEPOSIT' AND p.type = ?";
        Double result = jdbcTemplate.queryForObject(sql, Double.class, userId, productType);
        result = result != null ? result : 0.0;

        cacheService.putTransactionSum(cacheKey, result);
        return result;
    }

    public Double getTotalSpendingAmountByType(String userId, String productType) {
        String cacheKey = "spending_" + userId + "_" + productType;
        Double cachedValue = cacheService.getTransactionSum(cacheKey);
        if (cachedValue != null) {
            return cachedValue;
        }

        String sql = "SELECT SUM(t.amount) FROM user_transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND t.type = 'SPENDING' AND p.type = ?";
        Double result = jdbcTemplate.queryForObject(sql, Double.class, userId, productType);
        result = result != null ? result : 0.0;

        cacheService.putTransactionSum(cacheKey, result);
        return result;
    }

    public int getTransactionCountByType(String userId, String productType) {
        String cacheKey = "count_" + userId + "_" + productType;
        Boolean cachedValue = cacheService.getUserOf(cacheKey);
        if (cachedValue != null) {
            return cachedValue ? 1 : 0;
        }

        String sql = "SELECT COUNT(*) FROM user_transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND p.type = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);

        cacheService.putUserOf(cacheKey, count > 0);
        return count;
    }

    public Map<String, Double> getTransactionSumsByType(String userId, String productType) {
        String sql = """
            SELECT t.type, SUM(t.amount) 
            FROM user_transactions t 
            JOIN products p ON t.product_id = p.id
            WHERE t.user_id = ? AND p.type = ?
            GROUP BY t.type
        """;

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql, userId, productType);
        Map<String, Double> sums = new HashMap<>();
        for (Map<String, Object> result : results) {
            String type = (String) result.get("type");
            Double sum = (Double) result.get("sum");
            sums.put(type, sum != null ? sum : 0.0);
        }
        return sums;
    }
}
