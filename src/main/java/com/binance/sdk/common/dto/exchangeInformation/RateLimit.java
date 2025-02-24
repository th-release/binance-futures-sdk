package com.binance.sdk.common.dto.exchangeInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class RateLimit {
    @Expose
    private String interval;
    @Expose
    private int intervalNum;
    @Expose
    private int limit;
    @Expose
    private String rateLimitType;
}
