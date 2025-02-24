package com.binance.sdk.common.dto.leverageBrackets;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import com.binance.sdk.common.binance.BinanceRequest;

@Getter
@Builder
public class LeverageBracketsRequest extends BinanceRequest {
    @Expose
    private String symbol;
    @Expose
    private long recvWindow;
    @Expose
    private long timestamp;
}
