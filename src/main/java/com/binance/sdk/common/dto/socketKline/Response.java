package com.binance.sdk.common.dto.socketKline;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String stream;
    private SocketKline data;
}
