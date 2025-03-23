package com.skillbox.cryptobot.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.skillbox.cryptobot.service.PriceCurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;


@Service
@Slf4j
public class CentralBankClient {
    String filePath = "cbr_central_bank.json";

    private final ObjectMapper mapper;
    private final HttpClient httpClient;
    private final HttpGet httpGet;

    public CentralBankClient(@Value("${api.cbr.dailyRates}") String uri) {
        httpGet = new HttpGet(uri);
        mapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }

    public double getRubPrice() throws IOException {

        try {
            String jsonResponse = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
            return mapper.readTree(EntityUtils.toString(httpClient.execute(httpGet).getEntity()))
                    .path("Valute").path("USD").path("Value").asDouble();
        } catch (IOException e) {
            log.error("error price RUB");
            throw e;
        }
    }
    public void saveJsonToFile(String jsonStr, String filePath){
        ObjectMapper mapper = new ObjectMapper();
        try{
            JsonNode jsonNode = mapper.readTree(jsonStr);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), jsonNode);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
