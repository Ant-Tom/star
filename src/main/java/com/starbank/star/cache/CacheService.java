package com.starbank.star.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final Cache<String, Boolean> userOfCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    private final Cache<String, Boolean> activeUserOfCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    private final Cache<String, Double> transactionSumCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    public void putUserOf(String key, Boolean value) {
        userOfCache.put(key, value);
    }

    public Boolean getUserOf(String key) {
        return userOfCache.getIfPresent(key);
    }

    public void putActiveUserOf(String key, Boolean value) {
        activeUserOfCache.put(key, value);
    }

    public Boolean getActiveUserOf(String key) {
        return activeUserOfCache.getIfPresent(key);
    }

    public void putTransactionSum(String key, Double value) {
        transactionSumCache.put(key, value);
    }

    public Double getTransactionSum(String key) {
        return transactionSumCache.getIfPresent(key);
    }

    public void deleteCache () {
        userOfCache.invalidateAll();
        activeUserOfCache.invalidateAll();
        transactionSumCache.invalidateAll();
    }
}