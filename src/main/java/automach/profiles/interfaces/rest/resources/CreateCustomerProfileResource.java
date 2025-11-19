package automach.profiles.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload para crear un perfil de cliente.
 */
public record CreateCustomerProfileResource(
        @NotNull Long iamUserId,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email
) { }