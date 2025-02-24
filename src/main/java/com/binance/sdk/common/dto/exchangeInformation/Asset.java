package com.binance.sdk.common.dto.exchangeInformation;

import com.google.gson.annotations.Expose;
import lombok.Getter;

@Getter
public class Asset {
    @Expose
    private String asset;
    @Expose
    private boolean marginAvailable;
    @Expose
    private float autoAssetExchange;
}
