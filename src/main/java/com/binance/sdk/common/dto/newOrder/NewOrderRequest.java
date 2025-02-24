package com.binance.sdk.common.dto.newOrder;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import com.binance.sdk.common.binance.BinanceRequest;

@Getter
@Builder
public class NewOrderRequest extends BinanceRequest {
    @Expose
    private String symbol;
    @Expose
    private String side;
    @Expose
    private String positionSide;
    @Expose
    private String type;
    @Expose
    private String timeInForce;
    @Expose
    private double quantity;
    @Expose
    private String reduceOnly;
    @Expose
    private double price;
    @Expose
    private String newClientOrderId;
    @Expose
    private double stopPrice;
    @Expose
    private String closePosition;
    @Expose
    private double activationPrice;
    @Expose
    private double callbackRate;
    @Expose
    private String workingType;
    @Expose
    private String priceProtect;
    @Expose
    private String newOrderRespType;
    @Expose
    private String priceMatch;
    @Expose
    private String selfTradePreventionMode;
    @Expose
    private long goodTillDate;
    @Expose
    private long recvWindow;
    @Expose
    private long timestamp;
}
