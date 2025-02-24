package com.binance.sdk.common.binance;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Component;
import com.binance.sdk.common.Properties.BinanceProperties;
import com.binance.sdk.common.utils.HmacUtil;

@Component
@RequiredArgsConstructor
public class Api {
    private final BinanceProperties binanceProperties;

    public <T> BinanceResponse<T> sendRequest(String endpoint, String method, BinanceRequest request, Class<T> responseType) {
        try {
            OkHttpClient client = new OkHttpClient();

            String queryString = request.toQueryString();

            // 서명 생성
            String signature = HmacUtil.hmacSha256(queryString, binanceProperties.getSecret());

            // 서명을 쿼리 스트링에 추가
            queryString = queryString + "&signature=" + signature;

            // URL 구성
            String url = "https://fapi.binance.com" + endpoint + "?" + queryString;

            if (binanceProperties.isLogging()){
                System.out.println("url: " + url);
            }

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .addHeader("X-MBX-APIKEY", binanceProperties.getApi());

            if (method.equalsIgnoreCase("POST")) {
                if (binanceProperties.isLogging()) {
                    System.out.println("method: POST");
                }
                requestBuilder.method("POST", RequestBody.create("", null));
            } else {
                if (binanceProperties.isLogging()) {
                    System.out.println("method: GET");
                }
                requestBuilder.method("GET", null);
            }

            Request req = requestBuilder.build();
            Response response = client.newCall(req).execute();

            if (!response.isSuccessful()) {
                if (binanceProperties.isLogging()) {
                    System.out.println("response: " + response.body().string());
                }
                response.close();
                return null;
            }

            String jsonResponse = response.body() != null ? response.body().string() : "{}";

            if (binanceProperties.isLogging()) {
                System.out.println("response: " + jsonResponse);
            }

            return new BinanceResponse<>(jsonResponse, responseType);
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println(endpoint + " Exception: " + e.getMessage());
            }
            return null;
        }
    }
}
