package com.example.currencyexchangenew.async;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.example.currencyexchangenew.holder.CurrencyRateHolder;
import com.example.currencyexchangenew.model.CurrencyRateModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RetrieveRatesTask extends Thread {

    public static final OkHttpClient CLIENT = new OkHttpClient();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final String URL = "https://developers.paysera.com/tasks/api/currency-exchange-rates";

    // TODO fix this
    {
        sendRequest();
    }
//    {
//        try {
//            System.out.println("Running first time!");
//            CurrencyRateHolder.setCurrencyRateModel(OBJECT_MAPPER.readValue(
//                    MainActivity.class.getClassLoader().getResource("currency_rates.json"), CurrencyRateModel.class));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void start() {
        sendRequest();
    }

    private static void sendRequest() {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            System.out.println("Running not first time!");
            CurrencyRateHolder.setCurrencyRateModel(
                    OBJECT_MAPPER.readValue(response.body().string().getBytes(UTF_8), CurrencyRateModel.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
