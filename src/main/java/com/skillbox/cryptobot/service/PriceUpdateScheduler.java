package com.skillbox.cryptobot.service;


import com.skillbox.cryptobot.configuration.SchedulerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceUpdateScheduler {

    private final PriceCurrencyService currencyService;

    @Scheduled(fixedRateString = "${api.scheduler.rate}") //every 2 min
    public double updateBtcPrice(){
        double currentPrice = 0;
        try{
            currentPrice = currencyService.getBitcoinPrice();
            log.info("BTC currentPrice <<<-------  " + currentPrice);
        }catch (IOException e) {
            log.error("error fetch price btc");
        }
        return currentPrice;
    }
}
