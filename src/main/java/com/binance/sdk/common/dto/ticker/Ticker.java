package com.binance.sdk.common.dto.ticker;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class Ticker {
    @Expose
    private String symbol;
    @Expose
    private double price;
    @Expose
    private long time;
}
