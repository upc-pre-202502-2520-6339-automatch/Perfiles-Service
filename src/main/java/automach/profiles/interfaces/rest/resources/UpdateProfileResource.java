package automach.profiles.interfaces.rest.resources;

import automach.profiles.domain.model.valueobjects.BusinessType;
import automach.profiles.domain.model.valueobjects.RoleType;

/**
 * Payload para actualizar un perfil.
 * Todos los campos son opcionales (pueden venir nulos).
 */
public record UpdateProfileResource(
        String firstName,
        String lastName,
        String email,
        RoleType roleType,
        String ruc,
        BusinessType businessType,
        String businessName,
        String address,
        String phoneNumber
) { }
