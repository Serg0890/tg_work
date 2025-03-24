package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {

    Optional<Subscriber> findByTelegramId(Long telegramId);

    @Query("SELECT s FROM Subscriber s WHERE s.subscribedPrice <= :currentPrice")
    List<Subscriber> findByTargetPrice(@Param("currentPrice") Double currentPrice);

    @Query("SELECT s.subscribedPrice FROM Subscriber s WHERE s.telegramId = :telegramId")
    Optional<BigDecimal> findSubscriptionByTelegramId(Long telegramId);



}
