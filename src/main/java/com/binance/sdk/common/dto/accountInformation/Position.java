package com.binance.sdk.common.dto.accountInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class Position {
    @Expose
    private String symbol;
    @Expose
    private String positionSide;
    @Expose
    private double positionAmt;
    @Expose
    private double unrealizedProfit;
    @Expose
    private double isolatedMargin;
    @Expose
    private double notional;
    @Expose
    private double isolatedWallet;
    @Expose
    private double initialMargin;
    @Expose
    private double maintMargin;
    @Expose
    private long updateTime;
}
