package com.binance.sdk.common.dto.socketDepth;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookData {
    @SerializedName("e")
    private String eventType;

    @SerializedName("E")
    private long eventTime;

    @SerializedName("T")
    private long transactionTime;

    @SerializedName("s")
    private String symbol;

    @SerializedName("U")
    private long firstUpdateId;

    @SerializedName("u")
    private long finalUpdateId;

    @SerializedName("pu")
    private long previousFinalUpdateId;

    @SerializedName("b")
    private List<List<String>> bids;

    @SerializedName("a")
    private List<List<String>> asks;
}
