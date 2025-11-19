package automach.profiles.domain.model.commands;

import automach.profiles.domain.model.valueobjects.BusinessType;

/**
 * Comando para registrar un perfil de vendedor.
 */
public record RegisterSellerProfileCommand(
        Long iamUserId,
        String firstName,
        String lastName,
        String email,
        String ruc,
        BusinessType businessType,
        String businessName,
        String address,
        String phoneNumber
) { }