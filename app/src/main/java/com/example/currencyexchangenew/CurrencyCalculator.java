package com.example.currencyexchangenew;

import com.example.currencyexchangenew.holder.CurrencyRateHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

public class CurrencyCalculator {
    public static BigDecimal convert(String from, String to, BigDecimal amount) {
        Map<String, BigDecimal> currentRates = CurrencyRateHolder.getCurrencyRateModel().getRates();
        if (!currentRates.keySet().containsAll(Set.of(from, to))) {
            throw new IllegalArgumentException("Unknown currencies provided");
        }
        if (from.equals("EUR")) {
            return currentRates.get(to).multiply(amount).setScale(2);
        } else {
            return currentRates.get(to).divide(currentRates.get(from), 6, RoundingMode.HALF_UP).multiply(amount).setScale(2);
        }
    }
}
