package com.binance.sdk.common.dto.leverageBrackets;

import lombok.Getter;

@Getter
public class Brackets {
    private int bracket;
    private int initialLeverage;
    private int notionalCap;
    private int notionalFloor;
    private double maintMarginRatio;
    private int cum;
}
