package com.binance.sdk.common.dto.exchangeInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.util.List;

@Getter
public class ExchangeInformation {
    @Expose
    private List<Object> exchangeFilters;
    @Expose
    private List<RateLimit> rateLimits;
    @Expose
    private List<Asset> assets;
    @Expose
    private List<Symbol> symbols;
    @Expose
    private String timezone;

}
