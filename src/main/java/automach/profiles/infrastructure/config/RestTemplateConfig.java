package automach.profiles.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Value("${external.reniec.token}")
    private String reniecToken;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Interceptor para añadir el header Authorization
        ClientHttpRequestInterceptor authInterceptor = (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + reniecToken);
            return execution.execute(request, body);
        };

        restTemplate.setInterceptors(List.of(authInterceptor));
        return restTemplate;
    }
}
