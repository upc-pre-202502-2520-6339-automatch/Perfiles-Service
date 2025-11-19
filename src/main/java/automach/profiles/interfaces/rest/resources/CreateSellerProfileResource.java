package automach.profiles.interfaces.rest.resources;

import automach.profiles.domain.model.valueobjects.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Payload para crear un perfil de vendedor.
 */
public record CreateSellerProfileResource(
        @NotNull Long iamUserId,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank String ruc,
        @NotNull BusinessType businessType,
        @NotBlank String businessName,
        @NotBlank String address,
        @NotBlank String phoneNumber
) { }