package automach.profiles.domain.model.aggregates;

import automach.profiles.domain.model.valueobjects.BusinessType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Información de negocio asociada a un usuario vendedor.
 * Forma parte del agregado User.
 */
@Getter
@AllArgsConstructor
public class SellerProfile {

    private final String ruc;
    private final BusinessType businessType;
    private final String businessName;
    private final String address;
    private final String phoneNumber;
}
