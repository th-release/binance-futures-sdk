package com.binance.sdk.common.dto.leverageBrackets;

import lombok.Getter;

import java.util.List;

@Getter
public class LeverageBrackets {
    private String symbol;
    private double notionalCoef;
    private List<Brackets> brackets;
}
