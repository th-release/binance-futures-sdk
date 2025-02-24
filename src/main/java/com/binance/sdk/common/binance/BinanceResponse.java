package com.binance.sdk.common.binance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

@Getter
public class BinanceResponse<T> {
    private final T data;
    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public BinanceResponse(String json, Class<T> clazz) {
        this.data = gson.fromJson(json, clazz);
    }
}
