package com.binance.sdk.common.dto.ticker;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticker {
    @Expose
    private String symbol;
    @Expose
    private double price;
    @Expose
    private String priceString;
    @Expose
    private long time;
}
