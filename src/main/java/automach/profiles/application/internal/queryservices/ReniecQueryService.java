package automach.profiles.application.internal.queryservices;

import automach.profiles.infrastructure.external.ReniecApiClient;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Application service que orquesta la consulta a la API de RENIEC
 * para enriquecer/perfil o validar información de usuarios.
 */
@Service
public class ReniecQueryService {

    private final ReniecApiClient reniecApiClient;

    public ReniecQueryService(ReniecApiClient reniecApiClient) {
        this.reniecApiClient = reniecApiClient;
    }

    /**
     * Obtiene la información de una persona en RENIEC dado su DNI.
     */
    public Map<String, Object> getPersonInfo(String dni) {
        return reniecApiClient.getPersonByDni(dni);
    }
}


