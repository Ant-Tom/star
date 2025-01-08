package com.starbank.star.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Example: Fetching user's transaction data (to be expanded as needed)
    public Double getTotalDepositAmountByType(String userId, String productType) {
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND t.type = 'DEPOSIT' AND p.type = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, userId, productType) != null
                ? jdbcTemplate.queryForObject(sql, Double.class, userId, productType)
                : 0.0;

    }

    public Double getTotalSpendingAmountByType(String userId, String productType) {
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                "JOIN products p ON t.product_id = p.id " +
                "WHERE t.user_id = ? AND t.type = 'SPENDING' AND p.type = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, userId, productType) != null
                ? jdbcTemplate.queryForObject(sql, Double.class, userId, productType)
                : 0.0;

    }
}
