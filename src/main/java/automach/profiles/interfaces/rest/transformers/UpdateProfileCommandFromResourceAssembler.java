package automach.profiles.interfaces.rest.transformers;

import automach.profiles.domain.model.commands.UpdateProfileCommand;
import automach.profiles.domain.model.valueobjects.UserId;
import automach.profiles.interfaces.rest.resources.UpdateProfileResource;
import org.springframework.stereotype.Component;

/**
 * Ensamblador Resource -> Command para actualizar perfiles.
 */
@Component
public class UpdateProfileCommandFromResourceAssembler {

    public UpdateProfileCommand toCommand(Long id, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                UserId.of(id),
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.roleType(),
                resource.ruc(),
                resource.businessType(),
                resource.businessName(),
                resource.address(),
                resource.phoneNumber()
        );
    }
}
