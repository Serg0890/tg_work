package com.skillbox.cryptobot.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BtcPriceScheduler {

    private final CryptoCurrencyService currencyService;

    @Scheduled(cron = "0 */2 * * * *") //every 2 min
    public double fetchPriceBtc(){
        double currentPrice = 0;
        try{
            currentPrice = currencyService.getBitcoinPrice();
            log.info("btc currentPrice  " + currentPrice);
        }catch (IOException e) {
            log.error("error fetch price btc");
        }
        return currentPrice;
    }
}
