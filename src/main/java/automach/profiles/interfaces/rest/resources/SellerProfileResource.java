package automach.profiles.interfaces.rest.resources;

import automach.profiles.domain.model.valueobjects.BusinessType;

/**
 * Representación REST de los datos de negocio del vendedor.
 */
public record SellerProfileResource(
        String ruc,
        BusinessType businessType,
        String businessName,
        String address,
        String phoneNumber
) { }
