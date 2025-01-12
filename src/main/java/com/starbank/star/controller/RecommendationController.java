package com.starbank.star.controller;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(
            summary = "Get recommendations for a user",
            description = "Returns a list of recommendations for the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved recommendations"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/recommendation/{userId}")
    public Map<String, Object> getRecommendations(
            @Parameter(description = "UUID of the user", required = true)
            @PathVariable String userId) {
        List<RecommendationDTO> recommendations = recommendationService.getRecommendations(UUID.fromString(userId));
        Map<String, Object> response = new HashMap<>();
        response.put("user_id", userId);
        response.put("recommendations", recommendations);
        return response;
    }
}
