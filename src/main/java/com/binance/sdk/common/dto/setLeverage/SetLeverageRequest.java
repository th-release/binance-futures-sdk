package com.binance.sdk.common.dto.setLeverage;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import com.binance.sdk.common.binance.BinanceRequest;

@Getter
@Builder
public class SetLeverageRequest extends BinanceRequest {
    @Expose
    private String symbol;
    @Expose
    private int leverage;
    @Expose
    private long recvWindow;
    @Expose
    private long timestamp;
}
