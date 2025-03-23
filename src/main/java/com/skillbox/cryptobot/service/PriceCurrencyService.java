package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.client.CentralBankClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class PriceCurrencyService {
    private final AtomicReference<Double> priceBTC = new AtomicReference<>();
    private final AtomicReference<Double> priceRub = new AtomicReference<>();
    private final BinanceClient clientBinance;
    private final CentralBankClient centralBankClient;

    public PriceCurrencyService(BinanceClient binanceClient, CentralBankClient centralBankClient) {
        this.clientBinance = binanceClient;
        this.centralBankClient = centralBankClient;
    }

    public double getRubPrice() throws IOException {
        if (priceRub.get() == null) {
            priceRub.set(centralBankClient.getRubPrice());
        }
        return priceRub.get();
    }

    public double getBitcoinPrice() throws IOException {
        if (priceBTC.get() == null) {
            priceBTC.set(clientBinance.getBitcoinPrice());
        }
        return priceBTC.get();
    }
}
