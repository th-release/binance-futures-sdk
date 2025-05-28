package com.binance.sdk.common.dto.socketDepth;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @SerializedName("stream")
    private String stream;

    @SerializedName("data")
    private OrderBookData data;
}
