package com.starbank.star.bot;

import com.starbank.star.DTO.RecommendationDTO;
import com.starbank.star.service.RecommendationService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.UUID;

public class StarBankBot extends TelegramLongPollingBot {

    private final RecommendationService recommendationService;

    public StarBankBot(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public String getBotUsername() {
        return "${telegram.bot.username}"; // можно заменить на реальное имя, если необходимо
    }

    @Override
    public String getBotToken() {
        return "${telegram.bot.token}";   // аналогично, можно использовать настройки из properties
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
                String usernameParam = text.replace("/recommend ", "").trim();
                handleRecommendation(chatId, usernameParam);
            } else {
                sendMessage(chatId, "Неизвестная команда. Используйте /recommend username.");
            }
        }
    }

    private void handleRecommendation(String chatId, String usernameParam) {
        try {
            // Если поиск ведется по UUID, можно преобразовать usernameParam в UUID;
            // Если поиск по имени, можно изменить логику.
            UUID userId = UUID.fromString(usernameParam);
            String fullName = recommendationService.getUserFullName(userId);
            if (fullName == null) {
                sendMessage(chatId, "Пользователь не найден.");
                return;
            }
            List<RecommendationDTO> recommendations = recommendationService.getRecommendations(userId);
            if (recommendations.isEmpty()) {
                sendMessage(chatId, "Здравствуйте " + fullName + "!\nРекомендаций для вас пока нет.");
            } else {
                StringBuilder response = new StringBuilder("Здравствуйте " + fullName + "!\n\nНовые продукты для вас:\n");
                for (RecommendationDTO recommendation : recommendations) {
                    response.append("- ").append(recommendation.getText()).append("\n");
                }
                sendMessage(chatId, response.toString());
            }
        } catch (IllegalArgumentException e) {
            sendMessage(chatId, "Пользователь не найден.");
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
