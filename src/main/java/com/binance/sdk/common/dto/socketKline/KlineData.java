package com.binance.sdk.common.dto.socketKline;

import com.google.gson.annotations.SerializedName;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KlineData {
    @SerializedName("t")
    private long startTime;

    @SerializedName("T")
    private long closeTime;

    @SerializedName("s")
    private String symbol;

    @SerializedName("i")
    private String interval;

    @SerializedName("o")
    private double openPrice;

    @SerializedName("c")
    private double closePrice;

    @SerializedName("h")
    private double highPrice;

    @SerializedName("l")
    private double lowPrice;

    @SerializedName("v")
    private double baseAssetVolume;

    @SerializedName("x")
    private boolean isClosed;

    @SerializedName("q")
    private double quoteAssetVolume;

    @SerializedName("V")
    private double takerBuyBaseAssetVolume;

    @SerializedName("Q")
    private double takerBuyQuoteAssetVolume;
}
