package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.service.TelegramMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {

    private final TelegramMessageService telegramMessageService;
    private final SubscriberService subscriberService;


    @Override
    public String getCommandIdentifier() {
        return "get_subscription";
    }

    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long chatId = message.getChatId();

        Optional<Subscriber> user = subscriberService.getUserSubscriber(chatId);

        if (user.isPresent()) {
            BigDecimal price = user.get().getSubscribedPrice();
            if (price != null) {
                telegramMessageService.sendMessage(absSender, user.get().getTelegramId(),
                        "Вы подписаны на стоимость биткоина  " + price
                        + " USD");
            } else {
                telegramMessageService.sendMessage(absSender, chatId, " Активные подписки отсутствуют");

            }
        }else {
            telegramMessageService.sendMessage(absSender, chatId, "зарегистрируй себя командой /start");
            return;
        }

        telegramMessageService.sendMessage(absSender, chatId, arguments[0]);


    }
}