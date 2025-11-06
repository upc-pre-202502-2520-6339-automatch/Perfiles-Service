package automach.profiles.domain.model.aggregates;

import automach.profiles.domain.model.valueobjects.RoleType;
import automach.profiles.domain.model.valueobjects.UserId;

public class User {

    private final UserId id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleType roleType;
    private SellerProfile sellerProfile; // Solo si es vendedor

    public User(UserId id, String firstName, String lastName, String email, RoleType roleType, SellerProfile sellerProfile) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleType = roleType;
        this.sellerProfile = sellerProfile;
    }

    // Métodos de fábrica
    public static User createCustomer(String firstName, String lastName, String email) {
        return new User(UserId.generate(), firstName, lastName, email, RoleType.CUSTOMER, null);
    }

    public static User createSeller(String firstName, String lastName, String email, SellerProfile sellerProfile) {
        return new User(UserId.generate(), firstName, lastName, email, RoleType.SELLER, sellerProfile);
    }

    // Getters
    public UserId getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public RoleType getRoleType() { return roleType; }
    public SellerProfile getSellerProfile() { return sellerProfile; }
}
