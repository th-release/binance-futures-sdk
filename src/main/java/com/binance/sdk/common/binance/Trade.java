package com.binance.sdk.common.binance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.binance.sdk.common.dto.newOrder.NewOrderRequest;
import com.binance.sdk.common.dto.newOrder.NewOrderResponse;
import com.binance.sdk.common.dto.setMarginType.SetMarginTypeRequest;
import com.binance.sdk.common.dto.setMarginType.SetMarginTypeResponse;

@Component
@RequiredArgsConstructor
public class Trade {
    private final Api binanceApi;

    public BinanceResponse<NewOrderResponse> NewOrder(NewOrderRequest request) {
        return binanceApi.sendRequest("/fapi/v1/order", "POST", request, NewOrderResponse.class);
    }

    public BinanceResponse<SetMarginTypeResponse> setMarginType(SetMarginTypeRequest request) {
        return binanceApi.sendRequest("/fapi/v1/marginType", "POST", request, SetMarginTypeResponse.class);
    }
}
