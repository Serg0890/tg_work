package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


/**
 * Обработка команды начала работы с ботом
 */
@Service
@AllArgsConstructor
@Slf4j
public class StartCommand implements IBotCommand {
    private final SubscriberService subscriberService;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        Long chatId = message.getChatId();
        boolean isNeverUser = subscriberService.registerUser(chatId);
        String responceText = isNeverUser ? """
                вы успешно зарегистрировались 
                """
                : "вы уже были ранее зарегистрированы";

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("""
                Привет! я готов к работе, скажу курсы валют.
                мои команды пока что:
                 /subscribe [число] - подписаться на уведомления курса BTC
                 /get_price_BTC - получить стоимость биткоина
                 /get_price_RUB - стоимость рубля
                 /get_subscription - получить инфо о подписке
                 /unsubscribe - отменить подписку
                """ + responceText);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}