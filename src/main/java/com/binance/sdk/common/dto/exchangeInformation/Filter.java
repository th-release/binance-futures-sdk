package com.binance.sdk.common.dto.exchangeInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class Filter {
    @Expose
    private String filterType;
    @Expose
    private double maxPrice;
    @Expose
    private double minPrice;
    @Expose
    private double tickSize;
    @Expose
    private double maxQty;
    @Expose
    private double minQty;
    @Expose
    private double stepSize;
    @Expose
    private int limit;
    @Expose
    private double notional;
    @Expose
    private double multiplierUp;
    @Expose
    private double multiplierDown;
    @Expose
    private int multiplierDecimal;
}
