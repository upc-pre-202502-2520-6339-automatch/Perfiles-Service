package automach.profiles.infrastructure.persistence.jpa;

import automach.profiles.domain.model.valueobjects.BusinessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representación embebida de los datos de negocio del vendedor en la tabla users.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class SellerProfileEmbeddable {

    private String ruc;

    @Enumerated(EnumType.STRING)
    private BusinessType businessType;

    private String businessName;
    private String address;
    private String phoneNumber;
}



