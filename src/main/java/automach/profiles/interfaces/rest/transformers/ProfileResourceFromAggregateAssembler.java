package automach.profiles.interfaces.rest.transformers;

import automach.profiles.domain.model.aggregates.SellerProfile;
import automach.profiles.domain.model.aggregates.User;
import automach.profiles.interfaces.rest.resources.ProfileResource;
import automach.profiles.interfaces.rest.resources.SellerProfileResource;
import org.springframework.stereotype.Component;

/**
 * Ensamblador agregado User -> ProfileResource.
 */
@Component
public class ProfileResourceFromAggregateAssembler {

    public ProfileResource toResource(User user) {
        SellerProfileResource sellerResource = null;

        SellerProfile sellerProfile = user.getSellerProfile();
        if (sellerProfile != null) {
            sellerResource = new SellerProfileResource(
                    sellerProfile.getRuc(),
                    sellerProfile.getBusinessType(),
                    sellerProfile.getBusinessName(),
                    sellerProfile.getAddress(),
                    sellerProfile.getPhoneNumber()
            );
        }

        Long idValue = user.getId() != null ? user.getId().getValue() : null;

        return new ProfileResource(
                idValue,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoleType(),
                sellerResource
        );
    }
}
