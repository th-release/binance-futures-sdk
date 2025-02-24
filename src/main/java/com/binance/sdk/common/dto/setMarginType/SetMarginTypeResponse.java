package com.binance.sdk.common.dto.setMarginType;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetMarginTypeResponse {
    @Expose
    private String code;
    @Expose
    private String msg;
}
