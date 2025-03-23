package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.service.TelegramMessageService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;

/**
 * Обработка команды подписки на курс валюты
 */
@Service
@Slf4j
@AllArgsConstructor
public class SubscribeCommand implements IBotCommand {
    private final TelegramMessageService telegramMessageService;
    private final SubscriberService subscriberService;
    private final CryptoCurrencyService cryptoCurrencyService;



    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long chatId = message.getChatId();

        if (arguments.length == 0) {
            telegramMessageService.sendMessage(absSender, chatId, "не указана цена бтц после комманды /subscribe через пробел");
            return;
        }
        try {
            BigDecimal targetPrice = new BigDecimal(arguments[0]);
            subscriberService.subscriber(chatId, targetPrice);
            String priceBtc = TextUtil.toString(cryptoCurrencyService.getBitcoinPrice());

            telegramMessageService.sendMessage(absSender, chatId, "текущий курс бтц " + priceBtc + " usd");
            telegramMessageService.sendMessage(absSender, chatId, "подписка на бтц " + targetPrice + " usd");
        }catch (Exception e) {
            telegramMessageService.sendMessage(absSender, chatId, " error /subscribe " + arguments[0]);
        }

    }
}