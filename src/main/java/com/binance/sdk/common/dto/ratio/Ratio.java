package com.binance.sdk.common.dto.ratio;

import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ratio {
    @Expose
    private String symbol;
    @Expose
    private double longAccount;
    @Expose
    private double longShortRatio;
    @Expose
    private double shortAccount;
    @Expose
    private long timestamp;
}
