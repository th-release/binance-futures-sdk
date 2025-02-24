package com.binance.sdk.common.dto.accountInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class Asset {
    @Expose
    private String asset;
    @Expose
    private double walletBalance;
    @Expose
    private double unrealizedProfit;
    @Expose
    private double marginBalance;
    @Expose
    private double maintMargin;
    @Expose
    private double initialMargin;
    @Expose
    private double positionInitialMargin;
    @Expose
    private double openOrderInitialMargin;
    @Expose
    private double crossWalletBalance;
    @Expose
    private double crossUnPnl;
    @Expose
    private double availableBalance;
    @Expose
    private double maxWithdrawAmount;
    @Expose
    private long updateTime;
}
