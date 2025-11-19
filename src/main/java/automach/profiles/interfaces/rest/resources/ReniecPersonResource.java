package automach.profiles.interfaces.rest.resources;

import java.util.Map;

/**
 * Wrapper simple para la respuesta de RENIEC.
 * (Si luego conoces el esquema exacto, lo puedes tipar mejor).
 */
public record ReniecPersonResource(
        Map<String, Object> data
) { }
