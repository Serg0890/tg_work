package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.service.TelegramMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработка команды отмены подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class UnsubscribeCommand implements IBotCommand {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberService subscriberService;
    private final TelegramMessageService telegramMessageService;


    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long chatId = message.getChatId();

        boolean isDeleteds = subscriberService.removeSubscriber(chatId);
        if (isDeleteds) {
            telegramMessageService.sendMessage(absSender, chatId, " подписка отменена");
        }else {
            telegramMessageService.sendMessage(absSender, chatId, " активных подписок небыло");
        }
    }
}