package automach.profiles.domain.model.query;


import automach.profiles.infrastructure.external.ReniecApiClient;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * Application service that provides user profile enrichment
 * or verification through RENIEC external API.
 */
@Service
public class ReniecQueryService {

    private final ReniecApiClient reniecApiClient;

    public ReniecQueryService(ReniecApiClient reniecApiClient) {
        this.reniecApiClient = reniecApiClient;
    }

    /**
     * Retrieves person information from RENIEC given a DNI number.
     */
    public Map<String, Object> getPersonInfo(String dni) {
        return reniecApiClient.getPersonByDni(dni);
    }
}