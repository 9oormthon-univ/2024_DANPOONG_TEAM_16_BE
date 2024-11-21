package danpoong.mohaeng.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;


@Configuration
public class SwaggerConfig {
    @Value("${swagger.server-url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .servers(List.of(
                        new Server().url(serverUrl).description("Server")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("모두의 여행") // API의 제목
                .description("[2024 kakao X goorm] [\uD83D\uDE86AI 생성 배리어프리 여행 코스 제공 서비스]") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }
}
