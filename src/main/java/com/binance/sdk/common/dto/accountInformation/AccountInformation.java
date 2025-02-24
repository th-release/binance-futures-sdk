package com.binance.sdk.common.dto.accountInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountInformation {
    @Expose
    private double totalInitialMargin;
    @Expose
    private double totalMaintMargin;
    @Expose
    private double totalWalletBalance;
    @Expose
    private double totalUnrealizedProfit;
    @Expose
    private double totalMarginBalance;
    @Expose
    private double totalPositionInitialMargin;
    @Expose
    private double totalOpenOrderInitialMargin;
    @Expose
    private double totalCrossWalletBalance;
    @Expose
    private double totalCrossUnPnl;
    @Expose
    private double availableBalance;
    @Expose
    private double maxWithdrawAmount;
    @Expose
    private List<Asset> assets;
    @Expose
    private List<Position> positions;
}
