package com.binance.sdk.common.dto.exchangeInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.util.List;

@Getter
public class Symbol {
    @Expose
    private String symbol;
    @Expose
    private String pair;
    @Expose
    private String contractType;
    @Expose
    private long deliveryDate;
    @Expose
    private long onboardDate;
    @Expose
    private String status;
    @Expose
    private String baseAsset;
    @Expose
    private String quoteAsset;
    @Expose
    private String marginAsset;
    @Expose
    private long pricePrecision;
    @Expose
    private long quantityPrecision;
    @Expose
    private long baseAssetPrecision;
    @Expose
    private long quotePrecision;
    @Expose
    private String underlyingType;
    @Expose
    private List<String> underlyingSubType;
    @Expose
    private long settlePlan;
    @Expose
    private double triggerProtect;
    @Expose
    private List<Filter> filters;
    @Expose
    private List<String> OrderType;
    @Expose
    private List<String> timeInForce;
    @Expose
    private double liquidationFee;
    @Expose
    private double marketTakeBound;
}
