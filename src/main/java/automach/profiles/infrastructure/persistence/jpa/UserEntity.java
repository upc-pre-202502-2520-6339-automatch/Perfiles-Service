package automach.profiles.infrastructure.persistence.jpa;

import automach.profiles.domain.model.valueobjects.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA que representa la fila en la tabla "users".
 * Es la representación de infraestructura del agregado de dominio User.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "iam_user_id", nullable = false)
    private Long iamUserId;

    @Version
    private Long version;

    private String firstName;
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Embedded
    private SellerProfileEmbeddable sellerProfile;
}
