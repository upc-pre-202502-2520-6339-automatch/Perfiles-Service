package automach.profiles.interfaces.rest.resources;

import automach.profiles.domain.model.valueobjects.RoleType;

/**
 * Representación REST del perfil de usuario (cliente o vendedor).
 */
public record ProfileResource(
        Long id,
        String firstName,
        String lastName,
        String email,
        RoleType roleType,
        SellerProfileResource sellerProfile
) { }
