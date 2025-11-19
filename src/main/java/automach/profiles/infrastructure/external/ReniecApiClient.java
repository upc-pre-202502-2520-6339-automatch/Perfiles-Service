package automach.profiles.infrastructure.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Cliente externo para consultar la API de RENIEC (DNI -> datos personales).
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
     * Llama a la API externa de RENIEC para obtener datos de una persona por DNI.
     */
    public Map<String, Object> getPersonByDni(String dni) {
        String url = UriComponentsBuilder
                .fromHttpUrl(reniecBaseUrl)
                .queryParam("numero", dni)
                .toUriString();

        ResponseEntity<Map<String, Object>> response =
                restTemplate.getForEntity(url, (Class<Map<String, Object>>)(Class<?>)Map.class);

        return response.getBody();
    }
}
