package automach.profiles.domain.model.commands;

import automach.profiles.domain.model.valueobjects.BusinessType;
import automach.profiles.domain.model.valueobjects.RoleType;
import automach.profiles.domain.model.valueobjects.UserId;

/**
 * Comando para actualizar la información de un perfil existente.
 * (Los campos pueden venir nulos si no se desean modificar.)
 */
public record UpdateProfileCommand(
        UserId userId,
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
