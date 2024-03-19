package com.example.currencyexchangenew.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class CurrencyRateModel {
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;

    public CurrencyRateModel(String base, LocalDate date, Map<String, BigDecimal> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public CurrencyRateModel() {
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
