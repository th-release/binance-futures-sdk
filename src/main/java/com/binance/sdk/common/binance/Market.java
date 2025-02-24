package com.binance.sdk.common.binance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import com.binance.sdk.common.Properties.BinanceProperties;
import com.binance.sdk.common.dto.exchangeInformation.ExchangeInformation;
import com.binance.sdk.common.dto.kline.Kline;
import com.binance.sdk.common.dto.setLeverage.SetLeverageRequest;
import com.binance.sdk.common.dto.setLeverage.SetLeverageResponse;
import com.binance.sdk.common.dto.ticker.Ticker;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Market {

    private final BinanceProperties binanceProperties;
    private final Api binanceApi;

    public ExchangeInformation exchangeInformation() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fapi.binance.com/fapi/v1/exchangeInfo")
                .get()
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (binanceProperties.isLogging()) {
                System.out.println("response status: " + response.code());
            }

            if (!response.isSuccessful()) {
                System.out.println("is not successful || status: " + response.code());
                System.out.println(response.body().string());
                response.close();
                return null;
            }

            String responseData = response.body().string();

            if (binanceProperties.isLogging()) {
                System.out.println("response");
                System.out.println(responseData);
            }

            return exchangeInformationFromJson(responseData);
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println("Exchange Exception: " + e.getMessage());
            }
            return null;
        }
    }

    private ExchangeInformation exchangeInformationFromJson(String json) {
        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().
                create();

        return gson.fromJson(json, ExchangeInformation.class);
    }

    public Ticker tickerV1(String symbol) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fapi.binance.com/fapi/v1/ticker/price?symbol=" + symbol)
                .get()
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                response.close();
                return null;
            }

            String responseData = Objects.requireNonNull(response.body()).string();
            return tickerFromJson(responseData);
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println("Ticker Exception: " + e.getMessage());
            }
            return null;
        }
    }

    public Ticker tickerV2(String symbol) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fapi.binance.com/fapi/v2/ticker/price?symbol=" + symbol)
                .get()
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                response.close();
                return null;
            }

            String responseData = Objects.requireNonNull(response.body()).string();
            return tickerFromJson(responseData);
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println("Ticker Exception: " + e.getMessage());
            }
            return null;
        }
    }

    private Ticker tickerFromJson(String json) {
        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().
                create();

        return gson.fromJson(json, Ticker.class);
    }

    public List<Kline> kline(
            String symbol,
            String interval,
            int limit
    ) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://fapi.binance.com/fapi/v1/klines?symbol=" + symbol + "&interval=" + interval + "&limit=" + limit)
                .get()
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (binanceProperties.isLogging()) {
                    System.out.println("is not successful || status: " + response.code());
                    System.out.println(response.body().string());
                }
                response.close();
                return new ArrayList<>();
            }

            String responseData = response.body().string();

            if (binanceProperties.isLogging()) {
                System.out.println("response");
                System.out.println(responseData);
            }

            return klineFromJson(responseData);
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println("Kline Exception: " + e.getMessage());
            }
            return new ArrayList<>();
        }
    }

    private List<Kline> klineFromJson(String json) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Type listType = new TypeToken<List<List<Object>>>() {}.getType();
        List<List<Object>> rawList = gson.fromJson(json, listType);

        List<Kline> klineList = new ArrayList<>();
        for (List<Object> entry : rawList) {
            klineList.add(
                    Kline.builder()
                            .openTime(((Number) entry.get(0)).longValue())
                            .open(Double.parseDouble(entry.get(1).toString()))
                            .high(Double.parseDouble(entry.get(2).toString()))
                            .low(Double.parseDouble(entry.get(3).toString()))
                            .close(Double.parseDouble(entry.get(4).toString()))
                            .volume(Double.parseDouble(entry.get(5).toString()))
                            .closeTime(((Number) entry.get(6)).longValue())
                            .quoteAssetVolume(Double.parseDouble(entry.get(7).toString()))
                            .takerBaseVolume(Double.parseDouble(entry.get(9).toString()))
                            .quoteAssetVolume(Double.parseDouble(entry.get(10).toString()))
                            .build()
            );
        }

        if (binanceProperties.isLogging()) {
            System.out.println("kline: " + klineList.size());
        }

        return klineList;
    }

    public BinanceResponse<SetLeverageResponse> setLeverage(SetLeverageRequest request) {
        return binanceApi.sendRequest("/fapi/v1/leverage", "POST", request, SetLeverageResponse.class);
    }
}
