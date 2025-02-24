package com.binance.sdk.common.dto.setLeverage;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetLeverageResponse {
    @Expose
    private int leverage;
    @Expose
    private double maxNotionalValue;
    @Expose
    private String symbol;
}
