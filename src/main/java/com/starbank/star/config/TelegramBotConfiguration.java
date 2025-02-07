package com.starbank.star.config;

import com.starbank.star.bot.StarBankBot;
import com.starbank.star.service.RecommendationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Configuration
public class TelegramBotConfiguration {

    @Bean
    public TelegramLongPollingBot starBankBot(RecommendationService recommendationService) {

        return new StarBankBot(recommendationService);
    }
}
