package automach.profiles.interfaces.rest.transformers;

import automach.profiles.domain.model.aggregates.User;
import automach.profiles.interfaces.rest.resources.CustomerProfileResource;
import org.springframework.stereotype.Component;

/**
 * Ensamblador User -> CustomerProfileResource (vista reducida).
 */
@Component
public class CustomerProfileResourceFromAggregateAssembler {

    public CustomerProfileResource toResource(User user) {
        Long idValue = user.getId() != null ? user.getId().getValue() : null;

        return new CustomerProfileResource(
                idValue,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
