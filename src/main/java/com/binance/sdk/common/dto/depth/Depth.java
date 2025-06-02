package com.binance.sdk.common.dto.depth;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Depth {
    @Expose
    private long id;
    @Expose
    private double price;
    @Expose
    private double qty;
    @Expose
    private double quoteQty;
    @Expose
    private long time;
    @Expose
    private boolean isBuyerMaker;
}
