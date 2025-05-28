package com.binance.sdk.common.binance;

import com.binance.sdk.common.dto.socketKline.SocketKline;
import com.binance.sdk.common.dto.ticker.Ticker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.binance.sdk.common.Properties.BinanceProperties;
import com.binance.sdk.common.dto.exchangeInformation.ExchangeInformation;
import com.binance.sdk.common.dto.kline.Kline;
import com.binance.sdk.common.dto.setLeverage.SetLeverageRequest;
import com.binance.sdk.common.dto.setLeverage.SetLeverageResponse;
import com.binance.sdk.common.dto.ticker.TickerResponse;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class Market {

    private final BinanceProperties binanceProperties;
    private final Api binanceApi;
    private final Map<String, BinanceCustomWebSocketClient> clients = new ConcurrentHashMap<>();
    private final ScheduledExecutorService heartbeatScheduler = Executors.newScheduledThreadPool(4);
    public record SocketCustomCallbackArgs(String id, String message) {}

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
            TickerResponse ticker = tickerFromJson(responseData);

            return Ticker.builder()
                    .price(Double.parseDouble(ticker.getPrice()))
                    .priceString(ticker.getPrice())
                    .symbol(symbol)
                    .time(ticker.getTime())
                    .build();
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
            TickerResponse ticker = tickerFromJson(responseData);

            return Ticker.builder()
                    .price(Double.parseDouble(ticker.getPrice()))
                    .priceString(ticker.getPrice())
                    .symbol(symbol)
                    .time(ticker.getTime())
                    .build();
        } catch (Exception e) {
            if (binanceProperties.isLogging()) {
                System.out.println("Ticker Exception: " + e.getMessage());
            }
            return null;
        }
    }

    private TickerResponse tickerFromJson(String json) {
        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().
                create();

        return gson.fromJson(json, TickerResponse.class);
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

    public class BinanceCustomWebSocketClient extends WebSocketClient {
        private final String url;
        private final String id;
        private String message;
        private final Consumer<SocketCustomCallbackArgs> callback;


        public BinanceCustomWebSocketClient(URI serverUri, String id, Consumer<SocketCustomCallbackArgs> callback) {
            super(serverUri);
            this.url = serverUri.toString();
            this.id = id;
            this.callback = callback;
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            if (binanceProperties.isLogging()) {
                log.info("Connected to Binance WebSocket [ID: id: {}, URL: {}]", id, url);
            }
        }

        @Override
        @Async
        public void onMessage(String message) {
            this.message = message;
            callback.accept(new SocketCustomCallbackArgs(id, this.message));
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            if (binanceProperties.isLogging()) {
                log.info("Disconnected from Binance WebSocket [ID: {}]: {} (Code: {} Remote:{}", id, reason, code, remote);
            }
        }

        @Override
        public void onError(Exception ex) {
            if (binanceProperties.isLogging()) {
                log.error("Error occurred [ID: {}] {}", id, ex.toString());
            }
        }

        public void checkHeartbeat() {
            try {
                if (isOpen()) {
                    sendPing();
                    if (binanceProperties.isLogging()) {
                        log.info("Ping sent [ID: {}]", id);
                    }
                } else {
                    if (clients.get(id) != null) {
                        if (binanceProperties.isLogging()) {
                            log.warn("Connection already closed [ID: {}]\nReconnecting...", id);
                            this.reconnect();
                        }
                    }
                }
            } catch (Exception e) {
                if (binanceProperties.isLogging()) {
                    log.error("Heartbeat error [ID: {}], {}", id, e.getMessage());
                }
                closeConnection(1000, "Heartbeat failure");
            }
        }
    }

    // 허트비트 스케줄러 초기화
    @PostConstruct
    public void init() {
        // 최대 1000개 연결을 위해 4개 스레드로 허트비트 관리
        heartbeatScheduler.scheduleAtFixedRate(() -> {
            // 순회 중 제거 문제를 방지하기 위해 복사
            for (BinanceCustomWebSocketClient client : clients.values().toArray(new BinanceCustomWebSocketClient[0])) {
                try {
                    client.checkHeartbeat();
                } catch (Exception e) {
                    if (binanceProperties.isLogging()) {
                        log.error("Heartbeat error [ID: {}]: {}", client.id, e.getMessage());
                    }
                }
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public String createCustomSocketConnection(List<String> actives, Consumer<SocketCustomCallbackArgs> callback) {
        try {
            String id = UUID.randomUUID().toString();
            // example
            String stream = actives.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("/"));

            URI uri = new URI("wss://fstream.binance.com/stream?streams=" + stream);
            if (binanceProperties.isLogging()) {
                log.info("Created connection [ID: {}, URL: {}]", id, uri.toString());
            }
            BinanceCustomWebSocketClient client = new BinanceCustomWebSocketClient(uri, id, callback);
            clients.put(id, client);
            client.connect();
            return id;
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public boolean closeCustomSocketConnection(String id) {
        BinanceCustomWebSocketClient client = clients.get(id);
        if (client != null) {
            try {
                clients.remove(id);
                client.closeConnection(1000, "Closed by user request");
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }


    @PreDestroy
    public void shutdownSocketKlineConnections() {
        clients.clear();
        heartbeatScheduler.shutdown();
        clients.values().forEach(client -> {
            try {
                client.closeConnection(1000, "Application shutdown");
            } catch (Exception e) {
                if (binanceProperties.isLogging()) {
                    log.error("Error occurred [ID: {}] {}", client.id, e.getMessage());
                }
            }
        });
        try {
            if (!heartbeatScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                heartbeatScheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            heartbeatScheduler.shutdownNow();
        }
    }
}
