package automach.profiles.domain.model.aggregates;

import automach.profiles.domain.exceptions.InvalidProfileStateException;
import automach.profiles.domain.model.valueobjects.RoleType;
import automach.profiles.domain.model.valueobjects.UserId;
import lombok.Getter;


/**
 * Agregado raíz para los perfiles de usuario (cliente y vendedor).
 */
@Getter
public class User {

    /**
     * Puede ser null mientras el agregado aún no ha sido persistido.
     * La infraestructura lo completará cuando venga desde la BD.
     */
    private final UserId id;       // id propio de Profiles (si quieres mantenerlo)
    private final Long iamUserId;  // 🔹 id que viene de IAM

    private String firstName;
    private String lastName;
    private String email;
    private RoleType roleType;
    private SellerProfile sellerProfile; // Solo si es vendedor

    public User(UserId id,
                Long iamUserId,
                String firstName,
                String lastName,
                String email,
                RoleType roleType,
                SellerProfile sellerProfile) {

        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (roleType == null) {
            throw new IllegalArgumentException("Role type must not be null");
        }

        this.id = id;
        this.iamUserId = iamUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleType = roleType;
        this.sellerProfile = sellerProfile;
    }

    // --------- Factory methods ---------

    public static User createCustomer(Long iamUserId,
                                      String firstName,
                                      String lastName,
                                      String email) {
        return new User(
                null,
                iamUserId,
                firstName,
                lastName,
                email,
                RoleType.CUSTOMER,
                null
        );
    }


    public static User createSeller(Long iamUserId,
                                    String firstName,
                                    String lastName,
                                    String email,
                                    SellerProfile sellerProfile) {
        if (sellerProfile == null) {
            throw new InvalidProfileStateException("SellerProfile must not be null for SELLER role");
        }

        return new User(
                null,          // id interno de Profiles, lo pone la BD
                iamUserId,     // 👈 referencia a IAM
                firstName,
                lastName,
                email,
                RoleType.SELLER,
                sellerProfile
        );
    }


    // --------- Behavior methods ---------

    public void updateBasicInfo(String firstName, String lastName, String email) {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        }
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        }
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
    }

    public void promoteToSeller(SellerProfile sellerProfile) {
        if (sellerProfile == null) {
            throw new InvalidProfileStateException("SellerProfile must not be null when promoting to SELLER");
        }
        this.roleType = RoleType.SELLER;
        this.sellerProfile = sellerProfile;
    }

    public void downgradeToCustomer() {
        this.roleType = RoleType.CUSTOMER;
        this.sellerProfile = null;
    }
}