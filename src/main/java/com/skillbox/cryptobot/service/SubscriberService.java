package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    @Transactional
    public boolean registerUser(Long telegramId) {
        Optional<Subscriber> existingUser = subscriberRepository.findByTelegramId(telegramId);

        if (existingUser.isEmpty()) {
            Subscriber newSubscriber = new Subscriber();
            newSubscriber.setTelegramId(telegramId);
            newSubscriber.setSubscribedPrice(null); //price null
            subscriberRepository.save(newSubscriber);
            return true; //новый зареган
        }
        return false; // уже существует

    }

    public void subscriber(Long telegramId, BigDecimal price) {
        Optional<Subscriber> existingUser = subscriberRepository.findByTelegramId(telegramId);
        Subscriber subscription;
        if (existingUser.isPresent()) {
            subscription = existingUser.get();
            subscription.setSubscribedPrice(price); //update price
        }else{

            subscription = new Subscriber();            //create new user
            subscription.setTelegramId(telegramId);
            subscription.setSubscribedPrice(price);
        }
        subscriberRepository.save(subscription);

    }

    public Optional<Subscriber> getUserSubscriber(Long telegramId) {
        return subscriberRepository.findByTelegramId(telegramId);
    }

    public boolean removeSubscriber(Long telegramId) {
        Optional<Subscriber> subscriber = subscriberRepository.findByTelegramId(telegramId); //ищу в репо
        if (subscriber.isPresent()) { //если есть id
            Subscriber sub = subscriber.get(); // достаю его
            if (sub.getSubscribedPrice() != null) { // если подписан
                sub.setSubscribedPrice(null); //удаляю
                subscriberRepository.save(sub);//save
                return true; //тру если удалил подписку
            }else {
                return false; //если был и небыл подписан
            }
        }else {
            return false; //небыло если пользователя то и подписки
        }
    }







}
