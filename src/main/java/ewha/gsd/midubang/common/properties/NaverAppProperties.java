package ewha.gsd.midubang.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("naver-app")
@Component
@Data
public class NaverAppProperties {
    private String clientId;
    private String clientSecret;
}
