package com.binance.sdk.common.dto.ticker;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TickerResponse {
    @Expose
    private String symbol;
    @Expose
    private String price;
    @Expose
    private long time;
}
