package automach.profiles.domain.model.commands;

/**
 * Comando para registrar un perfil de cliente.
 */
public record RegisterCustomerProfileCommand(
        Long iamUserId,
        String firstName,
        String lastName,
        String email
) { }