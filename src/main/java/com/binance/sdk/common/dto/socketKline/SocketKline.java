package com.binance.sdk.common.dto.socketKline;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketKline {
    @SerializedName("e")
    private String eventType;

    @SerializedName("E")
    private long eventTime;

    @SerializedName("s")
    private String symbol;

    @SerializedName("k")
    private KlineData klineData;
}
