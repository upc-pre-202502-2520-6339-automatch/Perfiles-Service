package automach.profiles.interfaces.rest.transformers;

import automach.profiles.domain.model.commands.RegisterSellerProfileCommand;
import automach.profiles.interfaces.rest.resources.CreateSellerProfileResource;
import org.springframework.stereotype.Component;

/**
 * Ensamblador Resource -> Command para registrar vendedores.
 */
@Component
public class CreateSellerProfileCommandFromResourceAssembler {

    public RegisterSellerProfileCommand toCommand(CreateSellerProfileResource resource) {
        return new RegisterSellerProfileCommand(
                resource.iamUserId(),
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.ruc(),
                resource.businessType(),
                resource.businessName(),
                resource.address(),
                resource.phoneNumber()
        );
    }
}
