package com.skillbox.cryptobot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
@Service
@Slf4j
public class TelegramMessageService {

    public void sendMessage(AbsSender absSender, Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try{
            absSender.execute(message);
        }catch (Exception e){
            log.error("ошибка send message", e);
        }
    }

}
