package com.binance.sdk.common.dto.accountInformation;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import com.binance.sdk.common.binance.BinanceRequest;

@Builder
public class AccountInformationRequest extends BinanceRequest {
    @Expose
    private long recvWindow;
    @Expose
    private long timestamp;
}
