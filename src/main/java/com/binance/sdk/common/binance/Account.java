package com.binance.sdk.common.binance;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import com.binance.sdk.common.Properties.BinanceProperties;
import com.binance.sdk.common.dto.accountInformation.AccountInformation;
import com.binance.sdk.common.dto.accountInformation.AccountInformationRequest;
import com.binance.sdk.common.dto.leverageBrackets.LeverageBrackets;
import com.binance.sdk.common.dto.leverageBrackets.LeverageBracketsRequest;
import com.binance.sdk.common.utils.HmacUtil;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Account {
    private final Api binanceApi;
    private final BinanceProperties binanceProperties;

    public BinanceResponse<AccountInformation> accountInformationV3(AccountInformationRequest request) {
        return binanceApi.sendRequest("/fapi/v3/account", "GET", request, AccountInformation.class);
    }

    public List<LeverageBrackets> leverageBrackets(LeverageBracketsRequest request) {
        try {
            OkHttpClient client = new OkHttpClient();

            String queryString = request.toQueryString();

            // 서명 생성
            String signature = HmacUtil.hmacSha256(queryString, binanceProperties.getSecret());

            // 서명을 쿼리 스트링에 추가
            queryString = queryString + "&signature=" + signature;

            // URL 구성
            String url = "https://fapi.binance.com/fapi/v1/leverageBracket" + "?" + queryString;

            if (binanceProperties.isLogging()){
                System.out.println("url: " + url);
            }

            Request.Builder requestBuilder = new Request.Builder()
                    .url(url)
                    .addHeader("X-MBX-APIKEY", binanceProperties.getApi());

            requestBuilder.method("GET", null);

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

            Gson gson = new Gson();
            Type listType = new TypeToken<List<LeverageBrackets>>() {}.getType();

            return gson.fromJson(jsonResponse, listType);
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println("LeverageBrackets Exception: " + e.getMessage());
            }
            return null;
        }
    }
}
