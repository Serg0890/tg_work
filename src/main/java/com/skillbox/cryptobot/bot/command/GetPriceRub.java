package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.PriceCurrencyService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
@AllArgsConstructor
public class GetPriceRub implements IBotCommand {

    private final PriceCurrencyService priceCurrencyService;


    @Override
    public String getCommandIdentifier() {
        return "get_price_Rub";
    }

    @Override
    public String getDescription() {
        return "цена в рублях";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        try {
            answer.setText("текущая цена рубля " +
                    TextUtil.toString(priceCurrencyService.getRubPrice()) + " RUB");
            absSender.execute(answer);
        } catch (Exception e) {
            log.error("error to /get_price_rub методе", e);
        }

    }
}
