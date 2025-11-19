package automach.profiles.interfaces.rest.transformers;

import automach.profiles.domain.model.commands.RegisterCustomerProfileCommand;
import automach.profiles.interfaces.rest.resources.CreateCustomerProfileResource;
import org.springframework.stereotype.Component;

/**
 * Ensamblador Resource -> Command para registrar clientes.
 */
@Component
public class CreateCustomerProfileCommandFromResourceAssembler {

    public RegisterCustomerProfileCommand toCommand(CreateCustomerProfileResource resource) {
        return new RegisterCustomerProfileCommand(
                resource.iamUserId(),
                resource.firstName(),
                resource.lastName(),
                resource.email()
        );
    }
}
