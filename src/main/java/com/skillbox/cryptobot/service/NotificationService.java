package com.skillbox.cryptobot.service;


import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private CryptoBot cryptoBot;
    SubscriberRepository subscriberRepository;
    PriceCurrencyService priceCurrencyService;

    @Scheduled(fixedRateString = "${api.scheduler.notification}")
    public void findSubscribers() {
        double bitcoinPrice;
        try {
            bitcoinPrice = priceCurrencyService.getBitcoinPrice();
            List<Subscriber> subscribers = subscriberRepository.findByTargetPrice(bitcoinPrice);
            for (Subscriber subscriber : subscribers) {
                sendNotification(subscriber.getTelegramId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendNotification(Long chatId) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("пора покупать " + priceCurrencyService.getBitcoinPrice() + " цена валюты");
            cryptoBot.execute(sendMessage);
        } catch (TelegramApiException | IOException ex) {
            ex.printStackTrace();
        }

    }

}
