package com.example.currencyexchangenew.holder;

import com.example.currencyexchangenew.model.CurrencyRateModel;

public final class CurrencyRateHolder {

    private static CurrencyRateModel currencyRateModel;

    public static CurrencyRateModel getCurrencyRateModel() {
        return currencyRateModel;
    }

    public static void setCurrencyRateModel(CurrencyRateModel newCurrencyRateModel) {
        currencyRateModel = newCurrencyRateModel;
    }
}
