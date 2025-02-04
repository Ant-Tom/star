package com.starbank.star.bot;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.service.RecommendationService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.UUID;

import static jdk.javadoc.internal.tool.Main.execute;

public class StarBankBot extends TelegramLongPollingBot {

    private final RecommendationService recommendationService;

    public StarBankBot(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public String getBotUsername() {
        return "${telegram.bot.username}";
    }

    @Override
    public String getBotToken() {
        return "${telegram.bot.token}";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String text = message.getText();

            if ("/start".equalsIgnoreCase(text)) {
                sendMessage(chatId, "Добро пожаловать! Используйте команду /recommend username для получения рекомендаций.");
            } else if (text.startsWith("/recommend ")) {
                String username = text.replace("/recommend ", "").trim();
                handleRecommendation(chatId, username);
            } else {
                sendMessage(chatId, "Неизвестная команда. Используйте /recommend username.");
            }
        }
    }

    private void handleRecommendation(String chatId, String username) {
        try {
            UUID userId = UUID.fromString(username);
            List<RecommendationDTO> recommendations = recommendationService.getRecommendations(userId);

            if (recommendations.isEmpty()) {
                sendMessage(chatId, "Здравствуйте! Рекомендаций для вас пока нет.");
            } else {
                StringBuilder response = new StringBuilder("Здравствуйте! Новые продукты для вас:\n");
                for (RecommendationDTO recommendation : recommendations) {
                    response.append("- ").append(recommendation.getText()).append("\n");
                }
                sendMessage(chatId, response.toString());
            }
        } catch (Exception e) {
            sendMessage(chatId, "Пользователь не найден.");
        }
    }

    private void sendMessage(String chatId, String text) {
        try {
            execute(new SendMessage(chatId, text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
