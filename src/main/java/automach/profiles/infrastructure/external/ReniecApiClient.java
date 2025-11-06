package automach.profiles.infrastructure.external;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * External client to call RENIEC API for person data lookup by DNI.
 */
@Component
public class ReniecApiClient {

    private final RestTemplate restTemplate;
    private final String reniecBaseUrl;

    public ReniecApiClient(RestTemplate restTemplate,
                           @Value("${external.reniec.url}") String reniecBaseUrl) {
        this.restTemplate = restTemplate;
        this.reniecBaseUrl = reniecBaseUrl;
    }

    /**
     * Calls RENIEC external API to get personal data by DNI.
     */
    public Map<String, Object> getPersonByDni(String dni) {
        String url = UriComponentsBuilder
                .fromHttpUrl(reniecBaseUrl)
                .queryParam("numero", dni)
                .toUriString();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return response.getBody();
    }
}