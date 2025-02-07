package com.starbank.star.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Configuration
public class TelegramBotInitializer {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotInitializer.class);

    private final TelegramLongPollingBot starBankBot;

    public TelegramBotInitializer(TelegramLongPollingBot starBankBot) {
        this.starBankBot = starBankBot;
    }

    @PostConstruct
    public void registerBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(starBankBot);
            logger.info("Telegram Bot registered successfully.");
        } catch (TelegramApiException e) {
            logger.error("Error while registering Telegram Bot", e);
        }
    }
}
