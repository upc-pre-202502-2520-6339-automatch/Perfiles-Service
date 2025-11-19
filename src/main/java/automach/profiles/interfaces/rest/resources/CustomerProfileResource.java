package automach.profiles.interfaces.rest.resources;

/**
 * Vista reducida solo para clientes (sin datos de negocio).
 */
public record CustomerProfileResource(
        Long id,
        String firstName,
        String lastName,
        String email
) { }
