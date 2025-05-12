package com.binance.sdk.common.dto.kline;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Kline {
    @Expose
    private long openTime;
    @Expose
    private double open;
    @Expose
    private double high;
    @Expose
    private double low;
    @Expose
    private double close;
    @Expose
    private double volume;
    @Expose
    private long closeTime;
    @Expose
    private double quoteAssetVolume;
    @Expose
    private double takerBaseVolume;
    @Expose
    private double takerQuoteTime;
}
