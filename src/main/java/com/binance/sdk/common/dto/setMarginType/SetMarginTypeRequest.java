package com.binance.sdk.common.dto.setMarginType;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import com.binance.sdk.common.binance.BinanceRequest;

@Getter
@Builder
public class SetMarginTypeRequest extends BinanceRequest {
    @Expose
    private String symbol;
    @Expose
    private String marginType;
    @Expose
    private long recvWindow;
    @Expose
    private long timestamp;
}
