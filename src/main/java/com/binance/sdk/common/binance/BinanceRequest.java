package com.binance.sdk.common.binance;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class BinanceRequest {
    public String toQueryString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);

                if (value == null || value.toString().isEmpty()) {
                    continue;
                }

                // 기본값(0, false)인 경우 제외
                if ((value instanceof Number && ((Number) value).doubleValue() == 0) ||
                    (value instanceof Boolean && !((Boolean) value))) {
                    continue;
                }

                sb.append(URLEncoder.encode(field.getName(), StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(value.toString(), StandardCharsets.UTF_8))
                        .append("&");

            } catch (IllegalAccessException ignored) { }
        }

        return !sb.isEmpty() ? sb.substring(0, sb.length() - 1) : "";
    }
}
