package com.binance.sdk.common.dto.newOrder;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class NewOrderResponse {
    @Expose
    private String clientOrderId;
    @Expose
    private long cumQty;
    @Expose
    private long cumQuote;
    @Expose
    private long executedQty;
    @Expose
    private long orderId;
    @Expose
    private double avgPrice;
    @Expose
    private long origQty;
    @Expose
    private double price;
    @Expose
    private boolean reduceOnly;
    @Expose
    private String side;
    @Expose
    private String positionSide;
    @Expose
    private String status;
    @Expose
    private double stopPrice;
    @Expose
    private boolean closePosition;
    @Expose
    private String symbol;
    @Expose
    private String timeInForce;
    @Expose
    private String type;
    @Expose
    private String origType;
    @Expose
    private double activatePrice;
    @Expose
    private double priceRate;
    @Expose
    private long updateTime;
    @Expose
    private String workingType;
    @Expose
    private boolean priceProtect;
    @Expose
    private String priceMatch;
    @Expose
    private String selfTradePreventionMode;
    @Expose
    private long goodTillDate;
}
