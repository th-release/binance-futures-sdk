# Binance-Futures-SDK

## How To Use?
---

```java
package xyz.cth.trade;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.cth.sdk.EnabledBinance;

@EnabledBinance
@AllArgsConstructor
@SpringBootApplication
public class BaseApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
}
```

Spring Boot를 실행할 때, Application에서 `@EnabledBinance`를 사용하시면 됩니다.\
When running Spring Boot, you can use '@EnabledBinance' in the application.

에러가 발생 했을 경우 return 값은 `null`을 반환하게 됩니다.\
If an error occurs, the return value returns `null`.
